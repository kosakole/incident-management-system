package org.unibl.etf.services.implementation;

import org.springframework.stereotype.Service;
import org.unibl.etf.models.dto.City;
import org.unibl.etf.repositories.CityRepository;
import org.unibl.etf.services.CityService;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;

    public CityServiceImpl(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public List<City> getAll() {
        return cityRepository.findAll();
    }

    @Override
    public City getByName(String name) {
        return cityRepository.findByName(name);
    }
}
