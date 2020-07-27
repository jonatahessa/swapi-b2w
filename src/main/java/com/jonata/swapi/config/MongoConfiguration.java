package com.jonata.swapi.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import org.springframework.context.annotation.Configuration;

public class MongoConfiguration {
    MongoClient mongoClient = MongoClients.create(
    "mongodb+srv://swapiUser:Br@sil8000@cluster0.hidhh.mongodb.net/planet?retryWrites=true&w=majority");
    MongoDatabase database = mongoClient.getDatabase("planet");
}