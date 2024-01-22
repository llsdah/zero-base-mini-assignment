package com.example.zerobase.zerobaseminiassignment.controller;

import com.example.zerobase.zerobaseminiassignment.model.PostModel;
import com.example.zerobase.zerobaseminiassignment.model.ResultMessageModel;
import com.example.zerobase.zerobaseminiassignment.service.HashTagManageService;
import com.example.zerobase.zerobaseminiassignment.service.PostManageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/hashTags")
public class HashTagManageController {

    @Autowired
    private HashTagManageService hashTagManageService;

    /**
     * 해시태그 조회하기
     * @param hashTagName
     * @return
     */
    @GetMapping("/{hashTagName}")
    @ResponseBody
    public ResultMessageModel getHashTag(@PathVariable("hashTagName") String hashTagName){
        log.info("[START] HashTagManageController getHashTag");

        ResultMessageModel result = hashTagManageService.find(hashTagName);

        if(result.getMessageCode().startsWith("S")){
            result.setMessageContent("[SUCCESS]:getHashTag");
        }else {
            result.setMessageContent("[FAIL]:getHashTag");
        }

        log.info("[END] HashTagManageController getHashTag");
        return result;
    }

    /**
     * 전체 태그 조회
     * @return
     */
    @GetMapping("/all")
    @ResponseBody
    public ResultMessageModel getHashTags(){
        log.info("[START] HashTagManageController getHashTags");

        ResultMessageModel result = hashTagManageService.findAll();

        if(result.getMessageCode().startsWith("S")){
            result.setMessageContent("[SUCCESS]:getHashTags");
        }else {
            result.setMessageContent("[FAIL]:getHashTags");
        }

        log.info("[END] HashTagManageController getHashTags");
        return result;
    }

}
