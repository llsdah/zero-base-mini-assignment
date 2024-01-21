package com.example.zerobase.zerobaseminiassignment.model;


import lombok.Getter;
import lombok.Setter;

/**
 * 결과 메세지 모델
 */
@Getter
public class ResultMessageModel {

    private String messageCode;
    @Setter
    private String messageContent;
    @Setter
    private Object data;

    public ResultMessageModel(String messageCode, String messageContent,Object data) {
        this.messageCode = messageCode;
        this.messageContent = messageContent;
        this.data = data;
    }

    public ResultMessageModel(String messageCode, Object data) {
        this.messageCode = messageCode;
        this.data = data;
    }

    public ResultMessageModel(String messageCode, String messageContent) {
        this.messageCode = messageCode;
        this.messageContent = messageContent;
    }

    public ResultMessageModel(String messageCode) {
        this.messageCode = messageCode;
    }

    @Override
    public String toString() {
        return "ResultMessageModel{" +
                "messageCode='" + messageCode + '\'' +
                ", messageContent='" + messageContent + '\'' +
                ", data=" + data +
                '}';
    }
}
