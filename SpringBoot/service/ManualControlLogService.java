package com.capstone.project.service;

import com.capstone.project.model.entity.ManualControlLog;
import com.capstone.project.repository.ManualControlLogRepository;
import com.capstone.project.repository.OperatorRepository;
import com.capstone.project.repository.TrafficLightRepository;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ManualControlLogService {


    private final ManualControlLogRepository manualControlLogRepository;
    private final TrafficLightRepository trafficLightRepository;
    private final OperatorRepository operatorRepository;
    private final SequenceGeneratorService sequenceGeneratorService;

    public ManualControlLogService(ManualControlLogRepository manualControlLogRepository, TrafficLightRepository trafficLightRepository, OperatorRepository operatorRepository, SequenceGeneratorService sequenceGeneratorService) {
        this.manualControlLogRepository = manualControlLogRepository;
        this.trafficLightRepository = trafficLightRepository;

        this.operatorRepository = operatorRepository;
        this.sequenceGeneratorService = sequenceGeneratorService;
    }

    public ManualControlLog saveManualControlLog(ManualControlLog manualControlLog) {
        return manualControlLogRepository.save(manualControlLog);
    }

    //String logId,

    public ManualControlLog createLog(String operatorId, String lightId) {
        // Optional: Validate operatorId and lightId exist in their respective collections
        if (!operatorRepository.findByOperatorId(operatorId).isPresent()) {
            throw new RuntimeException("Operator not found");
        }

        if (!trafficLightRepository.findByLightId(lightId).isPresent()) {
            throw new RuntimeException("Traffic light not found");
        }

        long nextId = sequenceGeneratorService.generateSequence("logId");
        String formattedLogId=String.format("LOG-%04d",nextId);
        ManualControlLog log = new ManualControlLog();
       // log.setLogId(logId);

        log.setLogId(formattedLogId); // auto-set logId: String.valueOf(nextId)
        log.setCurrentTime(LocalDateTime.now());
        log.setOperatorId(operatorId);
        log.setLightId(lightId);

        try {
            return manualControlLogRepository.save(log);
        } catch (DuplicateKeyException e) {
            throw new RuntimeException("Log ID already exists!");
        }
     // return manualControlLogRepository.save(log);
    }


    public List<ManualControlLog> getAllLogs() {
        return manualControlLogRepository.findAll();
    }

    public Optional<ManualControlLog> getByLogId(String id) {
        return manualControlLogRepository.findByLogId(id);
    }

//    @DBRef
//   private Operator operator;
//
//    @DBRef

//   private TrafficLight trafficLight;
//    public ManualControlLog createLog(String logId, String operatorId, String lightId) {
//        Operator operator = operatorRepository.findByOperatorId(operatorId)
//                .orElseThrow(() -> new RuntimeException("Operator not found"));
//
//        TrafficLight light = trafficLightRepository.findByLightId(lightId)
//                .orElseThrow(() -> new RuntimeException("Traffic light not found"));
//
//        ManualControlLog log = new ManualControlLog();
//        log.setLogId(logId);
//        log.setCurrentTime(LocalDateTime.now());
//        log.setOperator(operator);
//        log.setTrafficLight(light);
//
//        return manualControlLogRepository.save(log);
//    }


}
