package com.example.zerobase.zerobaseminiassignment.controller;

import com.example.zerobase.zerobaseminiassignment.common.ResultMessageUtil;
import com.example.zerobase.zerobaseminiassignment.model.HashTagModel;
import com.example.zerobase.zerobaseminiassignment.model.ResultMessageModel;
import com.example.zerobase.zerobaseminiassignment.service.HashTagManageService;
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

        HashTagModel result = hashTagManageService.find(hashTagName);

        log.info("[END] HashTagManageController getHashTag");

        if(result != null){
            return ResultMessageUtil.success("getHashTag", result);
        }
        return ResultMessageUtil.fail();

    }

    /**
     * 전체 태그 조회
     * @return
     */
    @GetMapping("/all")
    @ResponseBody
    public ResultMessageModel getHashTags(){
        log.info("[START] HashTagManageController getHashTags");

        List<HashTagModel> result = hashTagManageService.findAll();

        log.info("[END] HashTagManageController getHashTags");
        if(result != null){
            return ResultMessageUtil.success("getHashTags", result);
        }
        return ResultMessageUtil.fail();
    }

    @DeleteMapping("{hashTagId}")
    @ResponseBody
    public ResultMessageModel deleteHashTags(@PathVariable Long hashTagId){

        boolean flag = hashTagManageService.delete(hashTagId);

        if(flag){
            return ResultMessageUtil.success("deleteHashTags");
        }

        return ResultMessageUtil.fail("deleteHashTags");
    }

}
