package com.capstone.project.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TrafficLightInfoDTO {

    private String location;
    private String currentStatus;
    private int numberOfWaitingVehicles;
    private double averageAreaSpeed;
    private double averageAreaVolume;
    private double averageAreaWaitingTime;








}
