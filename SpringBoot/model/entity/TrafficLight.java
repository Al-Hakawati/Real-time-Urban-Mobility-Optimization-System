package com.capstone.project.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Document(collection = "traffic_light")

public class TrafficLight {

    @Id
    private String db_id;
    private String lightId;
    private String location;
    private String currentStatus;
    private int numberOfWaitingVehicles;
    private double averageAreaSpeed;
    private double averageAreaVolume;
    private double averageAreaWaitingTime;
}
