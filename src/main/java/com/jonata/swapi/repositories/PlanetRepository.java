package com.jonata.swapi.repositories;

import java.util.List;

import com.jonata.swapi.model.Planet;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanetRepository extends MongoRepository<Planet, String> {

    List<Planet> findByNameLikeIgnoreCase(String name);

    Planet findByName(String name);
}