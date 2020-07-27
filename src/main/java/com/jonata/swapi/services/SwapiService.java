package com.jonata.swapi.services;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.jonata.swapi.consumers.HttpConsumer;
import com.jonata.swapi.dto.PlanetDTO;
import com.jonata.swapi.model.Planet;
import com.jonata.swapi.model.PlanetSwapi;
import com.jonata.swapi.services.exception.ConnectionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SwapiService {

    @Autowired
    private HttpConsumer httpConsumer;

    @Value("${swapi.url:}")
    private String swapiUrl;

    public List<PlanetDTO> findAll() throws ConnectionException {
        if (!testConnection()) {
            throw new ConnectionException("No connection detect.");
        }
        List<Planet> allPlanets = new ArrayList<>();
        List<PlanetDTO> allPlanetsDto = new ArrayList<>();
        ResponseEntity<PlanetSwapi> planet = null;
        int page = 0;
        do {
            page++;
            planet = httpConsumer.getRequisition(swapiUrl + "/planets/?page=" + page);
            List<Planet> plt = Arrays.asList(planet.getBody().getResults());
            allPlanets.addAll(plt);
        } while (planet.getBody().getNext() != null);

        for (Planet pln : allPlanets) {
            pln.setAparitions(pln.getFilms().length);
            allPlanetsDto.add(new PlanetDTO(pln));
        }
        return allPlanetsDto;
    }

    public PlanetDTO findOneByName(String planetName) throws ConnectionException {
        if (!testConnection()) {
            throw new ConnectionException("No connection detect.");
        }
        ResponseEntity<PlanetSwapi> response = httpConsumer.getRequisition(swapiUrl + "/planets/?search=" + planetName);
        List<Planet> plt = Arrays.asList(response.getBody().getResults());
        PlanetDTO planetDto = new PlanetDTO(plt.stream().findFirst().get());
        planetDto.setAparitions(aparitionsCount(planetDto.getName()));
        return planetDto;
    }

    public int aparitionsCount(String planetName) {
        int aparitions = 0;
        ResponseEntity<PlanetSwapi> planet = httpConsumer.getRequisition(swapiUrl + "/planets/?search=" + planetName);
        for (Planet p : planet.getBody().getResults()) {
            aparitions = p.getFilms().length;
        }
        return aparitions;
    }

    private boolean testConnection() {
        try {
            URL url = new URL(swapiUrl);
            HttpURLConnection huc = (HttpURLConnection) url.openConnection();
            huc.setRequestMethod("HEAD");

            int responseCode = huc.getResponseCode();

            return HttpURLConnection.HTTP_OK == responseCode;
        } catch (Exception e) {
            return false;
        }
    }
}