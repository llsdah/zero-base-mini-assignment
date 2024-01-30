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
        return ResultMessageUtil.resultMessage("S0001","해당 게시글을 차단했습니다.",result);
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
        return ResultMessageUtil.resultMessage("S0001","g해당 유저를 차단했습니다.",result);
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
        return ResultMessageUtil.resultMessage("S0001","차단된 내역을 전체 조회했습니다.",results);
    }

    @DeleteMapping("/{blockId}")
    @ResponseBody
    public ResultMessageModel deleteBlock(@PathVariable("blockId") Long blockId) {
        log.info("[START] BlockManageContioller deleteBlock");
        boolean result = blockManageService.delete(blockId);

        log.info("[END] BlockManageContioller deleteBlock");
        return ResultMessageUtil.resultMessage("S0001","해당 차단내역을 해제했습니다.",result);
    }
}
