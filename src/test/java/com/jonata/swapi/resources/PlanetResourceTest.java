package com.jonata.swapi.resources;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jonata.swapi.model.Planet;
import com.jonata.swapi.services.PlanetService;

@SpringBootTest
@AutoConfigureMockMvc
public class PlanetResourceTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private PlanetService planetService;

    private Planet mockedPlanet;

    @BeforeEach
	public void before() {
		mockedPlanet = new Planet();
		mockedPlanet.setName("Earth");
		mockedPlanet.setClimate("crazy");
		mockedPlanet.setTerrain("desert");
	}

	@Test
	public void insertPlaneValid201() throws Exception {
		mvc.perform(post("/planets").content(toJson(mockedPlanet)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
	}

	@Test
	public void insertPlanetDuplicated409() throws Exception {
		mvc.perform(post("/planets").content(toJson(mockedPlanet)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isConflict());
	}

	@Test
	public void insertPlanetUnsupportedMediaType415() throws Exception {
		mvc.perform(post("/planets").content(toJson(mockedPlanet))).andExpect(status().isUnsupportedMediaType());
	}

	@Test
	public void insertEmptyPlanet400() throws Exception {
		mvc.perform(post("/planets").content("").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void insertPlanetWithoutName400() throws Exception {
		mockedPlanet.setName(null);
		mvc.perform(post("/planets").content(toJson(mockedPlanet)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
    }

    @Test
	public void mustGetPlanetById() throws Exception {
		Planet obj = planetService.findOneByName(mockedPlanet.getName());
        mvc.perform(get("/planets/" +  obj.getId())
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
    }
    
    @Test
	public void mustDeletePlanet() throws Exception {
		Planet obj = planetService.findOneByName(mockedPlanet.getName());
        mvc.perform(delete("/planets/" +  obj.getId())
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());
    }

	private String toJson(Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
    

}