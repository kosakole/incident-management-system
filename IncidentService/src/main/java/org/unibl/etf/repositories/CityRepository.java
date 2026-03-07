package org.unibl.etf.repositories;

import org.springframework.stereotype.Repository;
import org.unibl.etf.models.dto.City;
import org.unibl.etf.models.dto.Location;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CityRepository {
    private static List<City> cities;

    static{
        cities = new ArrayList<>();
        cities.add(new City("Banja Luka", new Location(44.772182, 17.191000), 0.4));
        cities.add(new City("Sarajevo", new Location(43.856430, 18.413029), 0.6));
        cities.add(new City("Prijedor", new Location(44.980907, 16.713358), 0.33));
    }

    public List<City> findAll() {
        return cities;
    }

    public City findByName(String name){
        return cities.stream().filter(e -> e.getName().equals(name)).findFirst().orElse(null);
    }
}
