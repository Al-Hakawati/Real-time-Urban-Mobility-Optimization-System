package com.capstone.project.repository;

import com.capstone.project.model.entity.TrafficLight;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrafficLightRepository extends MongoRepository<TrafficLight,String> {

    @Query("{ 'lightId': ?0 }")
    Optional<TrafficLight> findByLightId(String lightId);


}
