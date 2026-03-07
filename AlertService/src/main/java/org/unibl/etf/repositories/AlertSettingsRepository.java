package org.unibl.etf.repositories;

import org.springframework.stereotype.Repository;
import org.unibl.etf.models.dto.AlertSettings;

@Repository
public class AlertSettingsRepository {

    private AlertSettings settings = new AlertSettings();

    public AlertSettings getSettings() {
        return settings;
    }

    public void setSettings(AlertSettings settings) {
        this.settings = settings;
    }
}