package org.unibl.etf.repositories;

import org.springframework.stereotype.Repository;
import org.unibl.etf.configs.AlertConfig;

@Repository
public class AlertSettingsRepository {

    private AlertConfig settings;

    public AlertSettingsRepository(AlertConfig settings) {
        this.settings = settings;
    }

    public AlertConfig getSettings() {
        return settings;
    }

    public void setSettings(AlertConfig settings) {
        this.settings = settings;
    }
}