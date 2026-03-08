package org.unibl.etf.services.implementation;

import org.springframework.stereotype.Service;
import org.unibl.etf.configs.AlertConfig;
import org.unibl.etf.repositories.AlertSettingsRepository;
import org.unibl.etf.services.AlertSettingsService;

@Service
public class AlertSettingsServiceImpl implements AlertSettingsService {

    private final AlertSettingsRepository repository;

    public AlertSettingsServiceImpl(AlertSettingsRepository repository) {
        this.repository = repository;
    }

    @Override
    public AlertConfig getSettings() {
        return repository.getSettings();
    }

    @Override
    public void setSettings(AlertConfig settings) {
        repository.setSettings(settings);
    }
}
