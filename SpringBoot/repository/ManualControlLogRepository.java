package com.capstone.project.repository;

import com.capstone.project.model.entity.ManualControlLog;
import com.capstone.project.model.entity.TrafficLight;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ManualControlLogRepository extends MongoRepository<ManualControlLog,String> {

    @Query("{ 'logId': ?0 }")
    Optional<ManualControlLog> findByLogId(String logId);
}
