package com.capstone.project.model.entity;


import lombok.Data;

@Data
public class OperatorLoginInfo {

    private String operatorId;
    private String password;

    public OperatorLoginInfo(String operatorId, String password) {
        this.operatorId = operatorId;
        this.password = password;
    }
}
