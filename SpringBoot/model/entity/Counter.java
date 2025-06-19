package com.capstone.project.model.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "counters")
@Data
public class Counter {
    @Id
    private String id;  // Example: "logId"
    private long seq;   // This holds the current number
}
