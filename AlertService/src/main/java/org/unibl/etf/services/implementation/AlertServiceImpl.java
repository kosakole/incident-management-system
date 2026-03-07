package org.unibl.etf.services.implementation;

import org.springframework.data.domain.Range;
import org.springframework.data.domain.Range.Bound;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.domain.geo.Metrics;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.unibl.etf.models.dto.AlertSettings;
import org.unibl.etf.models.dto.responses.IncidentResponse;
import org.unibl.etf.services.AlertListenerService;
import org.unibl.etf.services.AlertService;
import org.unibl.etf.services.AlertSettingsService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class AlertServiceImpl implements AlertService {

    private final String GEO_KEY = "incident_locations";
    private final String INDEX_KEY = "incidents:index";
    private final int INCIDENT_RETENTION_DAYS = 2;

    private final ReactiveRedisTemplate<String, IncidentResponse> redisTemplate;
    private final AlertListenerService alertListenerService;
    private final AlertSettingsService alertSettingsService;
    private final Sinks.Many<IncidentResponse> alertSink = Sinks.many().multicast().onBackpressureBuffer();

    public AlertServiceImpl(ReactiveRedisTemplate<String, IncidentResponse> redisTemplate, AlertListenerService alertListenerService, AlertSettingsService alertSettingsService) {
        this.redisTemplate = redisTemplate;
        this.alertListenerService = alertListenerService;
        this.alertSettingsService = alertSettingsService;
        listenToIncidentStream();
    }

    private void listenToIncidentStream(){
        this.alertListenerService.getIncidentStream()
                .subscribe(
                        incident -> {
                            processIncident(incident);
                        }
                );
    }

    private void processIncident(IncidentResponse newIncident) {
        if (newIncident.getLocation() == null ||
                newIncident.getLocation().getLongitude() == null ||
                newIncident.getLocation().getLatitude() == null) {
            return;
        }
        Point point = new Point(newIncident.getLocation().getLongitude(), newIncident.getLocation().getLatitude());
        redisTemplate.opsForGeo()
                .add(GEO_KEY, point, newIncident)
                .flatMap(addedCount -> {
                    Circle searchArea = new Circle(point, new Distance(alertSettingsService.getSettings().getRadiusInMeters(), Metrics.METERS));

                    return redisTemplate.opsForGeo()
                            .radius(GEO_KEY, searchArea)
                            .map(geoResult -> geoResult.getContent().getName())
                            .filter(incident -> {
                                LocalDateTime threshold = LocalDateTime.now().minusMinutes(alertSettingsService.getSettings().getTimeWindowMinutes());
                                return incident.getReportedAt().isAfter(threshold);
                            })
                            .count();
                })
                .subscribe(count -> {
                    if (count >= alertSettingsService.getSettings().getIncidentThreshold()) {
                        alertSink.tryEmitNext(newIncident);
                    }
                }, err -> {
                    err.printStackTrace();
                });

    }

    @Scheduled(fixedRate = 360000)
    public void cleanUpOldData() {
        long threshold = System.currentTimeMillis() - Duration.ofDays(INCIDENT_RETENTION_DAYS).toMillis();
        redisTemplate.opsForZSet()
                .rangeByScore(INDEX_KEY, Range.of(
                        Bound.<Double>unbounded(),
                        Bound.inclusive((double) threshold)
                ))
                .collectList()
                .flatMap(oldList -> {
                    if (oldList.isEmpty()) return Mono.empty();
                    IncidentResponse[] ids = oldList.toArray(new IncidentResponse[0]);
                    return Mono.zip(
                            redisTemplate.opsForGeo().remove(GEO_KEY, ids),
                            redisTemplate.opsForZSet().remove(INDEX_KEY, ids)
                    );
                })
                .subscribe(
                        res -> {},
                        err -> err.printStackTrace()
                );
    }


    @Override
    public void updateSettings(AlertSettings settings) {
        alertSettingsService.setSettings(settings);
    }

    public Flux<IncidentResponse> getAlertStream() {
        return alertSink.asFlux();
    }
}
