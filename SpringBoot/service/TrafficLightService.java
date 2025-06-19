package com.capstone.project.service;


import com.capstone.project.model.entity.Operator;
import com.capstone.project.model.entity.TrafficLight;
import com.capstone.project.repository.TrafficLightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrafficLightService {


    private final TrafficLightRepository trafficLightRepository;

    public TrafficLightService(TrafficLightRepository trafficLightRepository) {
        this.trafficLightRepository = trafficLightRepository;
    }

    public TrafficLight saveTrafficLight(TrafficLight light) {
        Optional<TrafficLight> existing = trafficLightRepository
                .findByLightId(light.getLightId());

        if (existing.isPresent()) {
            throw new IllegalArgumentException("Traffic Light with ID "
                    + light.getLightId() + " already exists.");
        }
        return trafficLightRepository.save(light);
    }

    public List<TrafficLight> findAllTrafficLights() {
        return trafficLightRepository.findAll();
    }


    public Optional<TrafficLight> findByLightId(String lightId) {
        return trafficLightRepository.findByLightId(lightId);
    }



    public TrafficLight updateLightStatusAndWeight(String lightId, String currentStatus,
                                                   int numberOfWaitingVehicles,double averageAreaSpeed,
                                                   double averageAreaVolume,double averageAreaWaitingTime) {
        Optional<TrafficLight> optionalLight = trafficLightRepository.findByLightId(lightId);
        if (optionalLight.isEmpty()) {
            return null;
        }

        TrafficLight light = optionalLight.get();
        light.setCurrentStatus(currentStatus);
        light.setNumberOfWaitingVehicles(numberOfWaitingVehicles);
        light.setAverageAreaSpeed(averageAreaSpeed);
        light.setAverageAreaVolume(averageAreaVolume);
        light.setAverageAreaWaitingTime(averageAreaWaitingTime);

        return trafficLightRepository.save(light);
    }


    public void updateTrafficLightStatus(String lightId, String currentStatus) {
        Optional<TrafficLight> optional = trafficLightRepository.findByLightId(lightId);
        if (optional.isEmpty()) {
            throw new RuntimeException("Traffic Light not found");
        }

        TrafficLight light = optional.get();
        light.setCurrentStatus(currentStatus);
        trafficLightRepository.save(light);
    }

}
