package org.unibl.etf.services.implementation;

import org.springframework.stereotype.Service;
import org.unibl.etf.models.dto.AlertSettings;
import org.unibl.etf.repositories.AlertSettingsRepository;
import org.unibl.etf.services.AlertSettingsService;

@Service
public class AlertSettingsServiceImpl implements AlertSettingsService {

    private final AlertSettingsRepository repository;

    public AlertSettingsServiceImpl(AlertSettingsRepository repository) {

        this.repository = repository;
    }

    @Override
    public AlertSettings getSettings() {
        return repository.getSettings();
    }

    @Override
    public void setSettings(AlertSettings settings) {
        repository.setSettings(settings);
    }
}
