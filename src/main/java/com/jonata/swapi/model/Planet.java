package com.jonata.swapi.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@Document(collection = "planet")
public class Planet implements Serializable {
	private static final long serialVersionUID = 1L;

    @Id
    String id;
    String name;
    String terrain;
    String climate;
    @JsonIgnore
    @Transient
    String [] films;

    public Planet(String id, String name, String terrain, String climate) {
        this.id = id;
        this.name = name;
        this.terrain = terrain;
        this.climate = climate;
    }
}