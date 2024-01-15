package com.example.zerobase.zerobaseminiassignment.model;

/**
 * 등록시간 모델
 */
public class RegistrationDateModel {
    private String registrationDate;
    public String getRegistrationDate() {
        return registrationDate;
    }
    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Override
    public String toString() {
        return "RegistrationDateModel{" +
                "registrationDate='" + registrationDate + '\'' +
                '}';
    }
}
