package com.capstone.project.controller;


import com.capstone.project.model.entity.Counter;
import com.capstone.project.model.entity.LogRequest;
import com.capstone.project.model.entity.ManualControlLog;
import com.capstone.project.model.entity.Operator;
import com.capstone.project.repository.ManualControlLogRepository;
import com.capstone.project.service.ManualControlLogService;
import jakarta.validation.Valid;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query; // ✅ CORRECT
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/manual-control-logs")
@CrossOrigin(origins = "*") // Allow cross-origin (important for HTML to access)
public class ManualControlLogController {


    private final ManualControlLogService logService;
    private final ManualControlLogRepository manualControlLogRepository;
    private final MongoOperations mongoOperations;


    public ManualControlLogController(ManualControlLogService logService, ManualControlLogRepository manualControlLogRepository, MongoOperations mongoOperations) {
        this.logService = logService;
        this.manualControlLogRepository = manualControlLogRepository;
        this.mongoOperations = mongoOperations;
    }

    @PostMapping("/create")
    public ResponseEntity<ManualControlLog> createLog(@RequestBody @Valid LogRequest request) {
        ManualControlLog savedLog = logService.createLog(
                //request.getLogId(),
                request.getOperatorId(),
                request.getLightId()
        );
        return ResponseEntity.ok(savedLog);
    }

    @GetMapping("/find/all")
    public ResponseEntity<List<ManualControlLog>> getAllLogs() {
        return ResponseEntity.ok(logService.getAllLogs());
    }

    @GetMapping("/findByLogId/{id}")
    public Optional<ManualControlLog> getByLogId(@PathVariable String id) {
        return logService.getByLogId(id);
    }

    @DeleteMapping("/reset")
    public ResponseEntity<String> resetLogsAndCounter() {
        // Delete all logs
        manualControlLogRepository.deleteAll();

        // Reset the counter for logId
        Query query = new Query(Criteria.where("_id").is("logId"));
        Update update = new Update().set("seq", 0);
        mongoOperations.updateFirst(query, update, Counter.class);

        return ResponseEntity.ok("All logs and logId counter reset to 0.");
    }




}
