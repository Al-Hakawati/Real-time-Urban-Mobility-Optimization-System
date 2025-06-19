

package com.capstone.project.model.entity;
public class OperatorDTO {
    private String operatorId;
    private String name;

    public OperatorDTO(String operatorId, String name) {
        this.operatorId = operatorId;
        this.name = name;
    }

    // Getters & Setters
    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
