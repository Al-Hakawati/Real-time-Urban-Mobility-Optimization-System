package com.capstone.project.model.entity;


import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;

@Data
public class LogRequest {

    //@NotNull(message = "logId must not be null")
    //@Indexed(unique = true)
   // private String logId;
    @NotNull(message = "operatorId must not be null")
    private String operatorId;
    @NotNull(message = "lightId must not be null")
    private String lightId;
}
