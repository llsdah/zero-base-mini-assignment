package com.example.zerobase.zerobaseminiassignment.controller;

import com.example.zerobase.zerobaseminiassignment.common.ResultMessageUtil;
import com.example.zerobase.zerobaseminiassignment.model.BlockModel;
import com.example.zerobase.zerobaseminiassignment.model.ResultMessageModel;
import com.example.zerobase.zerobaseminiassignment.service.BlockManageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/blocks")
public class BlockManageContioller {

    @Autowired
    private BlockManageService blockManageService;

    /**
     * 게시글 차단
     * @param postId
     * @return
     */
    @PostMapping("/post")
    @ResponseBody
    public ResultMessageModel postBlockPost(@RequestBody Long postId) {
        log.info("[StART] BlockManageContioller postBlockPost");

        BlockModel result = blockManageService.saveBlockPost(postId);
        log.info("[END] BlockManageContioller postBlockPost");
        if(result != null){
            return ResultMessageUtil.success("postBlockPost",result);
        }else {
            return ResultMessageUtil.fail();
        }

    }

    /**
     * 맴버 차단
     * @param memberId
     * @return
     */
    @PostMapping("/member")
    @ResponseBody
    public ResultMessageModel postBlockUser(@RequestBody Long memberId) {
        log.info("[START] BlockManageContioller postBlockUser");

        BlockModel result = blockManageService.saveBlockMember(memberId);

        log.info("[END] BlockManageContioller postBlockUser");
        if(result != null){
            return ResultMessageUtil.success("postBlockUser", result);
        }else {
            return ResultMessageUtil.fail();
        }

    }

    /**
     * 해당 맴버의 차단 전체 조회
     * @return
     */
    @GetMapping("/finds")
    @ResponseBody
    public ResultMessageModel getBlocks() {
        log.info("[START] BlockManageContioller getBlocks");
        List<BlockModel> results = blockManageService.findAll();

        log.info("[END] BlockManageContioller getBlocks");
        if(results != null){
            return ResultMessageUtil.success("getBlocks", results);
        }else {
            return ResultMessageUtil.fail();
        }

    }

    @DeleteMapping("/{blockId}")
    @ResponseBody
    public ResultMessageModel deleteBlock(@PathVariable("blockId") Long blockId) {
        log.info("[START] BlockManageContioller deleteBlock");
        boolean results = blockManageService.delete(blockId);

        log.info("[END] BlockManageContioller deleteBlock");
        if(results){
            return ResultMessageUtil.success("deleteBlock", results);
        }
        return ResultMessageUtil.fail();
    }
}
