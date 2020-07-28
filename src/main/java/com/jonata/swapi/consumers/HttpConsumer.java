package com.jonata.swapi.consumers;

import com.jonata.swapi.model.PlanetSwapi;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HttpConsumer {
    
    public ResponseEntity<PlanetSwapi> getRequisition(String url) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<PlanetSwapi> response = restTemplate.getForEntity(url, PlanetSwapi.class);

        return response;
    }
}