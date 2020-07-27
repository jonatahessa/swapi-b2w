package com.jonata.swapi.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.jonata.swapi.consumers.HttpConsumer;
import com.jonata.swapi.dto.PlanetDTO;
import com.jonata.swapi.model.Planet;
import com.jonata.swapi.repositories.PlanetRepository;
import com.jonata.swapi.services.exception.ObjectNotFoundException;
import com.jonata.swapi.services.exception.PlanetDuplicatedInDatabaseException;
import com.jonata.swapi.services.exception.PlanetInvalidAttribute;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlanetService {

  @Autowired
  PlanetRepository planetRepository;

  @Autowired
  HttpConsumer httpConsumer;

  public List<PlanetDTO> findAll() {
    List<Planet> list = planetRepository.findAll();
    List<PlanetDTO> listDto = list.stream().map(x -> new PlanetDTO(x)).collect(Collectors.toList());
    return listDto;
  }

  public Planet findById(String id) {
    Optional<Planet> obj = planetRepository.findById(id);
    return obj.orElseThrow(() -> new ObjectNotFoundException("Planet not found"));
  }

  public Planet insert(final Planet obj) throws PlanetInvalidAttribute {
    if (obj.getName() == null || obj.getName().isEmpty()) {
      throw new PlanetInvalidAttribute("The Planet name cannot be empty");
    }
    if (planetRepository.findByName(obj.getName()) != null) {
			throw new PlanetDuplicatedInDatabaseException(
					"The Planet name must be unique in the database");
		}
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

  public List<Planet> findByNameLike(String name) {
    return planetRepository.findByNameLikeIgnoreCase(name);
  }

  public Planet findOneByName(String name) {
    return planetRepository.findByName(name);
  }

  public Planet fromDTO(PlanetDTO objDto) {
    return new Planet(objDto.getId(), objDto.getName(), objDto.getTerrain(), objDto.getClimate());
  }
}