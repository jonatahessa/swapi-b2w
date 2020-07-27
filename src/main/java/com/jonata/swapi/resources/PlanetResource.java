package com.jonata.swapi.resources;

import java.net.URI;
import java.util.List;

import com.jonata.swapi.dto.PlanetDTO;
import com.jonata.swapi.model.Planet;
import com.jonata.swapi.resources.util.URL;
import com.jonata.swapi.services.PlanetService;
import com.jonata.swapi.services.SwapiService;
import com.jonata.swapi.services.exception.ConnectionException;
import com.jonata.swapi.services.exception.PlanetInvalidAttribute;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/planets")
@Api(value = "API REST STAR WARS PLANETS")
@CrossOrigin(origins = "*")
public class PlanetResource {

    @Autowired
    private PlanetService planetService;

    @Autowired
    private SwapiService swapiService;

    @GetMapping
    @ApiOperation(value = "Return a list of all planets from database.")
    public ResponseEntity<List<PlanetDTO>> findAll() {
        return ResponseEntity.ok().body(planetService.findAll());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Update a planet to database by ID.")
    public ResponseEntity<PlanetDTO> findById(@PathVariable String id) {
        PlanetDTO objDto = new PlanetDTO(planetService.findById(id));
        return ResponseEntity.ok().body(objDto);
   }

    @GetMapping("/fromAPI")
    @ApiOperation(value = "Return a list of all planets from https://swapi.dev.")
    public ResponseEntity<List<PlanetDTO>> findAllApiPlanets() throws ConnectionException {
        return ResponseEntity.ok().body(swapiService.findAll());
    }

    @GetMapping("/fromAPI/planet")
    @ApiOperation(value = "Return a list of all planets from https://swapi.dev.")
    public ResponseEntity<PlanetDTO> findOneApiPlanetByName(@RequestParam(value = "name", defaultValue = "") String name) throws ConnectionException {
        return ResponseEntity.ok().body(swapiService.findOneByName(name));
    }

    @GetMapping("/planet")
    @ApiOperation(value = "Return a Planet from database by 'like' Name.")
    public ResponseEntity<List<Planet>> findName(@RequestParam(value = "name", defaultValue = "") String name) {
        name = URL.decodeParam(name);
        return ResponseEntity.ok().body(planetService.findByNameLike(name));
    }

    @PostMapping
    @ApiOperation(value = "Insert a planet to database.")
    public ResponseEntity<Void> insert(@RequestBody PlanetDTO objDto) throws PlanetInvalidAttribute {
		Planet obj = planetService.fromDTO(objDto);
		obj = planetService.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
    }
    
    @PutMapping("/{id}")
    @ApiOperation(value = "Update a planet from database by ID.")
    public ResponseEntity<Void> update(@RequestBody PlanetDTO objDto, @PathVariable String id) {
        Planet obj = planetService.fromDTO(objDto);
        obj.setId(id);
        planetService.update(obj);
        return ResponseEntity.noContent().build();
   }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delets a planet from database by ID.")
 	public ResponseEntity<Void> delete(@PathVariable String id) {
		planetService.delete(id);
		return ResponseEntity.noContent().build();
	}
}