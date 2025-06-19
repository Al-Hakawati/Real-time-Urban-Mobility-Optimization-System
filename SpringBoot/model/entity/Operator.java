package com.capstone.project.model.entity;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Document(collection = "operator")
public class Operator {

    @Id
    private String db_id;
    @NotBlank(message = "Operator ID must not be blank")
    private String operatorId;
    @NotBlank(message = "Operator password must not be blank")
    private String password;
    @NotBlank(message = "Operator name must not be blank")
    private String name;
    @NotBlank(message = "Operator role must not be blank")
    private String role;
    @NotBlank(message = "Operator email must not be blank")
    private String contactInfo;

}
