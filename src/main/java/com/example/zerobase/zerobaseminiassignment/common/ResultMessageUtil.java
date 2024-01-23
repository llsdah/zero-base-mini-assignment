package com.example.zerobase.zerobaseminiassignment.common;

import com.example.zerobase.zerobaseminiassignment.model.ResultMessageModel;
import org.springframework.stereotype.Component;

@Component
public class ResultMessageUtil {

    // 그냥 편의상 
    public static ResultMessageModel success(){
        return new ResultMessageModel(
                "S0001"
        );
    }
    // 일반적인 모르겠는 에러
    public static ResultMessageModel success(String message){
        return new ResultMessageModel(
                "S0001",
                message
        );
    }
    public static ResultMessageModel success(String messageCode,String message){
        return new ResultMessageModel(
                messageCode,
                message
        );
    }

    public static ResultMessageModel success(String message,Object object){
        return new ResultMessageModel(
                "S0001",
                message,
                object
        );
    }
    public static ResultMessageModel success(String messageCode, String message,Object object){
        return new ResultMessageModel(
                messageCode,
                message,
                object
        );
    }

    // 그냥 편의상
    public static ResultMessageModel fail() {
        return new ResultMessageModel(
                "E0001"
        );
    }

    // 일반적인 모르겠는 에러
    public static ResultMessageModel fail(String message) {
        return new ResultMessageModel(
                "E0001",
                message
        );
    }

    public static ResultMessageModel fail(String messageCode,String message) {
        return new ResultMessageModel(
                messageCode,
                message
        );
    }

    public static ResultMessageModel fail(String messageCode ,String message,Object data) {
        return new ResultMessageModel(
                messageCode,
                message,
                data
        );
    }
}
