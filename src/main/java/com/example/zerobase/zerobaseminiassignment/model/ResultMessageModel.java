package com.example.zerobase.zerobaseminiassignment.model;


/**
 * 결과 메세지 모델
 */
public class ResultMessageModel {

    private String messageCode;
    private String messageContent;

    public ResultMessageModel(String messageCode, String messageContent) {
        this.messageCode = messageCode;

        this.messageContent = messageContent;
    }

    public String getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }
}
