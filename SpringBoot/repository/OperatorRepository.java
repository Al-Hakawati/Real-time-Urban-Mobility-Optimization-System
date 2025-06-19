package com.capstone.project.repository;

import com.capstone.project.model.entity.Operator;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OperatorRepository extends MongoRepository<Operator,String> {

    @Query("{ 'operatorId': ?0 }")
    Optional<Operator> findByOperatorId(String operatorId);

    @Query("{ 'operatorId': ?0, 'password': ?1 }")
    Optional<Operator> findByOperatorIdAndPassword(String operatorId, String password);
}
