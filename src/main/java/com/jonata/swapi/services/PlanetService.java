package com.jonata.swapi.services;

import java.util.List;
import java.util.Optional;

import com.jonata.swapi.dto.PlanetDTO;
import com.jonata.swapi.model.Planet;
import com.jonata.swapi.repositories.PlanetRepository;
import com.jonata.swapi.services.exception.ObjectNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlanetService {
    
    @Autowired
    PlanetRepository planetRepository;

    public List<Planet> findAll() {
        return planetRepository.findAll();
    }

    public Planet findById(String id) {
		Optional<Planet> obj = planetRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Planeta não encontrado"));
	}

    public Planet insert(final Planet obj) {
		obj.setId(null);
		return planetRepository.save(obj);
    }

    public Planet update(Planet obj) {
		Planet newObj = findById(obj.getId());
		updateData(newObj, obj);
		return planetRepository.save(newObj);
    }
    
    private void updateData(Planet newObj, Planet obj) {
		newObj.setName(obj.getName());
		newObj.setTerrain(obj.getTerrain());
    }
    
    public void delete(String id) {
		findById(id);
		planetRepository.deleteById(id);
    }
    
    public List<Planet> findByName(String name) {
        return planetRepository.findByNameIgnoreCase(name);
    }
    
    public Planet fromDTO(PlanetDTO objDto) {
		return new Planet(objDto.getId(), objDto.getName(), objDto.getTerrain());
	}
}