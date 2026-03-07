package org.unibl.etf.services;

import org.unibl.etf.models.dto.AlertSettings;

public interface AlertSettingsService {
    AlertSettings getSettings();
    void setSettings(AlertSettings settings);
}
