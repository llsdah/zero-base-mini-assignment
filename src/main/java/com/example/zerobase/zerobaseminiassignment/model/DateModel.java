package com.example.zerobase.zerobaseminiassignment.model;

/**
 * 시간 등록 모델
 */
public class DateModel {

    private String registrationDate;
    private String modificationDate;

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getModificationDate() {
        return modificationDate;
    }

    public void updateModificationDate(String modificationDate) {
        this.modificationDate = modificationDate;
    }


}
