package org.unibl.etf.services;

import org.unibl.etf.configs.AlertConfig;

public interface AlertSettingsService {
    AlertConfig getSettings();
    void setSettings(AlertConfig settings);
}
