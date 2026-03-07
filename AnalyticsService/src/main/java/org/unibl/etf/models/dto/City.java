package org.unibl.etf.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class City {
    private String name;
    private Location location;
    private double range;

    public boolean isLocationInCity(Location location){
        if(Math.abs(this.location.getLatitude() - location.getLatitude()) > range)
            return false;
        if(Math.abs(this.location.getLongitude() - location.getLongitude()) > range)
            return false;
        return true;
    }
}
