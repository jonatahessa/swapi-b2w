package com.jonata.swapi.dto;

import java.io.Serializable;

import com.jonata.swapi.model.Planet;

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
    String aparitions;
    
    public PlanetDTO(Planet obj) {
        id = obj.getId();
        name = obj.getName();
        terrain = obj.getTerrain();
    } 
}