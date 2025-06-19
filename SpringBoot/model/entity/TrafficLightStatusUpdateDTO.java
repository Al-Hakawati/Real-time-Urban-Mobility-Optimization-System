package com.capstone.project.model.entity;


import lombok.Data;

@Data
public class TrafficLightStatusUpdateDTO {
    private String lightId;
    private String currentStatus;
}
