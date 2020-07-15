package com.zukron.coronadata.model;

import org.threeten.bp.LocalDateTime;

/**
 * Project name is Corona Data App
 * Created by Zukron Alviandy R on 7/12/2020
 */
public class StatusData {
    private Integer confirmed;
    private Integer recovered;
    private Integer deaths;
    private LocalDateTime lastUpdate;

    public Integer getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Integer confirmed) {
        this.confirmed = confirmed;
    }

    public Integer getRecovered() {
        return recovered;
    }

    public void setRecovered(Integer recovered) {
        this.recovered = recovered;
    }

    public Integer getDeaths() {
        return deaths;
    }

    public void setDeaths(Integer deaths) {
        this.deaths = deaths;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
