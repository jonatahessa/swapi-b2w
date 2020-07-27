package com.jonata.swapi.dto;

import java.io.Serializable;

import com.jonata.swapi.model.Planet;
import com.jonata.swapi.model.PlanetSwapi;
import com.jonata.swapi.services.PlanetService;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PlanetDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    String id;
    String name;
    String terrain;
    String climate;
    int aparitions;

    public PlanetDTO(Planet obj) {
        id = obj.getId();
        name = obj.getName();
        terrain = obj.getTerrain();
        climate = obj.getClimate();
        aparitions = obj.getAparitions();
    }
}