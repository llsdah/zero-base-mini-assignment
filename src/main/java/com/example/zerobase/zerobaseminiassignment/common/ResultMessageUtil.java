package com.example.zerobase.zerobaseminiassignment.common;

import com.example.zerobase.zerobaseminiassignment.model.ResultMessageModel;
import org.springframework.stereotype.Component;

@Component
public class ResultMessageUtil {

    public static ResultMessageModel success(){
        return new ResultMessageModel(
                "S0001"
        );
    }

    public static ResultMessageModel success(Object object){
        return new ResultMessageModel(
                "S0001",
                object
        );
    }

    public static ResultMessageModel fail() {
        return new ResultMessageModel(
                "E0001"
        );
    }

}
