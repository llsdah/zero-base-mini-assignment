package com.example.zerobase.zerobaseminiassignment.model;

/**
 * 수정 시간 모델
 */
public class ModificationDateModel extends RegistrationDateModel{
    private String modificationDate;
    public String getModificationDate() {
        return modificationDate;
    }
    public void updateModificationDate(String modificationDate) {
        this.modificationDate = modificationDate;
    }

    @Override
    public String toString() {
        return "ModificationDateModel{" +
                "modificationDate='" + modificationDate + '\'' +
                "} " + super.toString();
    }
}
