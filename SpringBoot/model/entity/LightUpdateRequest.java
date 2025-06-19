package com.capstone.project.model.entity;

import lombok.Data;

@Data
public class LightUpdateRequest {
    private String lightId;
    private String currentStatus;
    private int numberOfWaitingVehicles;
    private double averageAreaSpeed;
    private double averageAreaVolume;
    private double averageAreaWaitingTime;

   
}
