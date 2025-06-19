package com.capstone.project.controller;

import com.capstone.project.model.entity.*;
import com.capstone.project.repository.TrafficLightRepository;
import com.capstone.project.service.TrafficLightService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/traffic-lights")
@CrossOrigin(origins = "*") // Allow cross-origin (important for HTML to access)
public class TrafficLightController {

    private final TrafficLightService trafficLightService;

    public TrafficLightController(TrafficLightService trafficLightService) {
        this.trafficLightService = trafficLightService;

    }
    @PostMapping("/create")
    public ResponseEntity<?> addTrafficLight(@Valid @RequestBody TrafficLight light) {

        try {
            TrafficLight saved = trafficLightService.saveTrafficLight(light);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }




    @GetMapping("/find/all")
    public List<TrafficLight> getAllTrafficLights()
    {

       return trafficLightService.findAllTrafficLights();
    }

    @GetMapping("/findBLightId/{lightId}")
    public Optional<TrafficLight> getTrafficLight(@PathVariable String lightId) {
        return trafficLightService.findByLightId(lightId); // Use custom query
    }

    @PutMapping("/update")
    public ResponseEntity<TrafficLight> updateLight(@RequestBody LightUpdateRequest request) {
        TrafficLight updated = trafficLightService.updateLightStatusAndWeight(
                request.getLightId(),
                request.getCurrentStatus(),
                request.getNumberOfWaitingVehicles(),
                request.getAverageAreaSpeed(),
                request.getAverageAreaVolume(),
                request.getAverageAreaWaitingTime());

        if (updated == null) {
            return ResponseEntity.notFound().build(); // 404
        }

        return ResponseEntity.ok(updated); // 200
    }


    @GetMapping("/info/{lightId}")
    public ResponseEntity<TrafficLightInfoDTO> getLightInfo(@PathVariable String lightId) {
        Optional<TrafficLight> optional = trafficLightService.findByLightId(lightId);

        if (optional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        TrafficLight light = optional.get();
        TrafficLightInfoDTO dto = new TrafficLightInfoDTO(
                light.getLocation(),
                light.getCurrentStatus(),
                light.getNumberOfWaitingVehicles(),
                light.getAverageAreaSpeed(),
                light.getAverageAreaVolume(),
                light.getAverageAreaWaitingTime()
                );

        return ResponseEntity.ok(dto);
    }


    @PutMapping("/update-status")
    public ResponseEntity<String> updateStatus(@RequestBody TrafficLightStatusUpdateDTO dto) {
        try {
            trafficLightService.updateTrafficLightStatus(dto.getLightId(), dto.getCurrentStatus());
            return ResponseEntity.ok("Status updated successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
