package com.jonata.swapi.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import com.jonata.swapi.dto.PlanetDTO;
import com.jonata.swapi.model.Planet;
import com.jonata.swapi.repositories.PlanetRepository;
import com.jonata.swapi.services.PlanetService;
import com.jonata.swapi.services.exception.ObjectNotFoundException;
import com.jonata.swapi.services.exception.PlanetDuplicatedInDatabaseException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class PlanetServiceTest {

    @Autowired
    private PlanetService service;

    @MockBean
    private PlanetRepository planetRepository;

    private String mockedId;

    private Planet mockedPlanet;

    @BeforeEach
	public void before() {
		mockedId = "ID";
		mockedPlanet = new Planet();
		mockedPlanet.setName("Earth");
		mockedPlanet.setClimate("crazy");
		mockedPlanet.setTerrain("desert");
	}

	@Test
	public void listAllPlanets() {
		List<PlanetDTO> list = service.findAll();
		assertThat(list).isNotNull();
	}

	@Test
	public void insertValidPlanet() throws Exception {
		when(planetRepository.save(mockedPlanet)).thenReturn(mockedPlanet);
		Planet newPlanet = service.insert(mockedPlanet);
		assertThat(newPlanet).isNotNull();
	}

	@Test
	public void insertDuplicatedPlanetOnDatabase() throws Exception {
		when(planetRepository.findByName(mockedPlanet.getName())).thenReturn(mockedPlanet);
		Assertions.assertThrows(PlanetDuplicatedInDatabaseException.class, () -> {
            service.insert(mockedPlanet);
          });
	}

	@Test
	public void findPlanetById() throws Exception {
		when(planetRepository.findById(mockedId)).thenReturn(Optional.of(mockedPlanet));
		Planet planetFound = service.findById(mockedId);
		assertThat(planetFound).isNotNull();
	}

	@Test
	public void findInexistentIdPlanet() throws Exception {
		Assertions.assertThrows(ObjectNotFoundException.class, () -> {
            service.findById(mockedId);
          });
	}

	@Test
	public void findPlanetByName() throws Exception {
		when(planetRepository.findByName(mockedPlanet.getName())).thenReturn(mockedPlanet);
		Planet planet = service.findOneByName(mockedPlanet.getName());
		assertThat(planet).isNotNull();
		assertThat(planet.getName()).isEqualTo(mockedPlanet.getName());
	}

	@Test
	public void deletePlanet() throws Exception {
		when(planetRepository.findById(mockedId)).thenReturn(Optional.of(mockedPlanet));
		doNothing().when(planetRepository).delete(mockedPlanet);
		service.delete(mockedId);
	}

	@Test
	public void tryDeletePlanetNotFoundException() throws Exception {
        when(planetRepository.findById(any())).thenReturn(Optional.empty());
        Assertions.assertThrows(ObjectNotFoundException.class, () -> {
            service.delete(mockedId);
          });
	}
}