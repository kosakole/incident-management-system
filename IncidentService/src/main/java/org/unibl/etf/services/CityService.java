package org.unibl.etf.services;

import org.unibl.etf.models.dto.City;

import java.util.List;

public interface CityService {
    List<City> getAll();
    City getByName(String name);
}
