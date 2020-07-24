package com.jonata.swapi.resources;

import java.net.URI;
import java.util.List;

import com.jonata.swapi.dto.PlanetDTO;
import com.jonata.swapi.model.Planet;
import com.jonata.swapi.resources.util.URL;
import com.jonata.swapi.services.PlanetService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/planets")
public class PlanetaResource {

    @Autowired
    private PlanetService planetService;

    @GetMapping
    public ResponseEntity<List<Planet>> findAll() {
        return ResponseEntity.ok().body(planetService.findAll());
    }

    @GetMapping("/planet")
    public ResponseEntity<List<Planet>> findName(@RequestParam(value = "name", defaultValue = "") String name) {
        name = URL.decodeParam(name);
        return ResponseEntity.ok().body(planetService.findByName(name));
    }

    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody PlanetDTO objDto) {
		Planet obj = planetService.fromDTO(objDto);
		obj = planetService.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody PlanetDTO objDto, @PathVariable String id) {
        Planet obj = planetService.fromDTO(objDto);
        obj.setId(id);
        planetService.update(obj);
        return ResponseEntity.noContent().build();
   }

    @DeleteMapping("/{id}")
 	public ResponseEntity<Void> delete(@PathVariable String id) {
		planetService.delete(id);
		return ResponseEntity.noContent().build();
	}
}