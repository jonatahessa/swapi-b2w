package com.jonata.swapi.services;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.jonata.swapi.consumers.HttpConsumer;
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

    public List<PlanetSwapi> findAll() throws ConnectionException {
        List<PlanetSwapi> allPlanets = new ArrayList<>();
        ResponseEntity<PlanetSwapi> planet = null;
        int page = 0;
        if (!testConnection()) {
            throw new ConnectionException("No connection detect.");
        }
        do {
            page++;
            planet = httpConsumer.getAllRequisition(swapiUrl + "/planets/?page=" + page);
            allPlanets.add(planet.getBody());
        } while (planet.getBody().getNext() != null);

        return allPlanets;
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