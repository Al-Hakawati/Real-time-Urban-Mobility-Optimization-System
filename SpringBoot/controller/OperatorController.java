package com.capstone.project.controller;


import com.capstone.project.model.entity.Operator;
import com.capstone.project.model.entity.OperatorDTO;
import com.capstone.project.model.entity.OperatorLoginInfo;
import com.capstone.project.model.entity.OperatorLoginRequest;
import com.capstone.project.service.OperatorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/operators")
@CrossOrigin(origins = "*") // Allow cross-origin (important for HTML to access)
public class OperatorController {


    private final OperatorService operatorService;

    public OperatorController(OperatorService operatorService) {
        this.operatorService = operatorService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> addOperator(@Valid @RequestBody Operator operator) {

          try {
        Operator saved = operatorService.saveOperator(operator);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    } catch (IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }
//        return ResponseEntity.ok(operatorService.saveOperator(operator)); -> return just operator
    }

    @GetMapping("/find/all")
    public ResponseEntity<List<Operator>> getAllOperators() {
        return ResponseEntity.ok(operatorService.getAllOperators());
    }

    @GetMapping("/findByOperatorId/{id}")
    public ResponseEntity<Operator> getOperatorById(@PathVariable String id) {

            return operatorService.getOperatorById(id)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());

    }

//    @CrossOrigin(origins = "*") // allow all origins
//    @GetMapping("/info/{id}")
//    public ResponseEntity<OperatorDTO> getOperatorIdAndName(@PathVariable String id) {
//        Optional<Operator> optionalOperator = operatorService.getOperatorByOperatorId(id);
//
//        if (optionalOperator.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        }
//
//        Operator operator = optionalOperator.get();
//        OperatorDTO dto = new OperatorDTO(operator.getOperatorId(), operator.getName());
//
//        return ResponseEntity.ok(dto);
//    }

    @CrossOrigin(origins = "*")  // allow frontend to access it
    @GetMapping("/info/{id}")
    public ResponseEntity<?> getOperatorIdAndName(@PathVariable String id) {
        Optional<Operator> optionalOperator = operatorService.getOperatorByOperatorId(id);

        if (optionalOperator.isEmpty()) {
            // Optional: return a JSON error instead of HTML
            Map<String, String> error = new HashMap<>();
            error.put("error", "Operator not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(error);  // cast error map to Object
        }

        Operator operator = optionalOperator.get();
        OperatorDTO dto = new OperatorDTO(operator.getOperatorId(), operator.getName());

        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(dto);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody OperatorLoginRequest loginRequest) {
        return operatorService.login(
                        loginRequest.getOperatorId(), loginRequest.getPassword())
                .map(op -> ResponseEntity.ok("Login successful"))
                .orElse(ResponseEntity.status(401).body("Invalid operator ID or password"));
    }

    @GetMapping("/operator/login-info/{id}")
    public ResponseEntity<OperatorLoginInfo> getLoginInfo(@PathVariable("id") String operatorId) {
        return operatorService.findLoginInfoByOperatorId(operatorId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(404).build());
    }


}
