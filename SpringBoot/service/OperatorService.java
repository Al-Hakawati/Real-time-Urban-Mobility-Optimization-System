package com.capstone.project.service;

import com.capstone.project.model.entity.Operator;
import com.capstone.project.model.entity.OperatorLoginInfo;
import com.capstone.project.repository.OperatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OperatorService {

    @Autowired
    private OperatorRepository operatorRepository;

    public Operator saveOperator(Operator operator) {
        Optional<Operator> existing = operatorRepository.findByOperatorId(operator.getOperatorId());

        if (existing.isPresent()) {
            throw new IllegalArgumentException("Operator with ID " + operator.getOperatorId() + " already exists.");
        }

        return operatorRepository.save(operator);
    }


    public List<Operator> getAllOperators() {
        return operatorRepository.findAll();
    }

    public Optional<Operator> getOperatorById(String id) {
        return operatorRepository.findByOperatorId(id);

    }


    public Optional<Operator> getOperatorByOperatorId(String operatorId) {
        return operatorRepository.findByOperatorId(operatorId);
    }

    public Optional<Operator> login(String operatorId, String password) {
        return operatorRepository.findByOperatorIdAndPassword(operatorId, password);
    }

    public Optional<OperatorLoginInfo> findLoginInfoByOperatorId(String operatorId) {
        return operatorRepository.findByOperatorId(operatorId)
                .map(op -> new OperatorLoginInfo(op.getOperatorId(), op.getPassword()));
    }


//    public ResponseEntity<String> createOperator(Operator newOperator) {
//        Optional<Operator> existing = operatorRepository.findByOperatorId(newOperator.getOperatorId());
//
//        if (existing.isPresent()) {
//            return ResponseEntity.status(HttpStatus.CONFLICT)
//                    .body("Operator with ID " + newOperator.getOperatorId() + " already exists.");
//        }
//
//        operatorRepository.save(newOperator);
//        return ResponseEntity.status(HttpStatus.CREATED)
//                .body("Operator created successfully.");
//    }


}
