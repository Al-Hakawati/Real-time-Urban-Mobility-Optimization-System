package com.capstone.project.model.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Document(collection = "manual_control_log")
public class ManualControlLog {

    @Id
    private String db_id;
    //@Indexed(unique = true)
    private String logId;
    private LocalDateTime currentTime;
    private String operatorId;
    private String lightId;

//    @DBRef
//   private Operator operator;
//
//    @DBRef
//   private TrafficLight trafficLight;

}
