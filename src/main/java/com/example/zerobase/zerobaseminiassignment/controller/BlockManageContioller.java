package com.example.zerobase.zerobaseminiassignment.controller;

import com.example.zerobase.zerobaseminiassignment.model.BlockModel;
import com.example.zerobase.zerobaseminiassignment.model.ResultMessageModel;
import com.example.zerobase.zerobaseminiassignment.service.BlockManageService;
import com.example.zerobase.zerobaseminiassignment.service.MemberManageService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/block")
public class BlockManageContioller {

    private static final Logger logger = LoggerFactory.getLogger(MemberManageController.class);

    @Autowired
    private BlockManageService blockManageService;

    /**
     * 게시글 차단
     * @param postId
     * @param request
     * @return
     */
    @PostMapping("/post")
    @ResponseBody
    public ResultMessageModel postBlockPost(@RequestBody Long postId, HttpServletRequest request) {

        HttpSession session = request.getSession();
        Long nowMemberId = (Long) session.getAttribute("memberID");

        BlockModel outputBlock = blockManageService.saveBlockPost(nowMemberId,postId);
        logger.info("[END] GroupManageController postBlock");
        return new ResultMessageModel(
                "S0001",
                "[SUCCESS]:postBlock|" + outputBlock.toString()
        );
    }

    /**
     * 맴버 차단
     * @param memberId
     * @param request
     * @return
     */
    @PostMapping("/member")
    @ResponseBody
    public ResultMessageModel postBlockUser(@RequestBody Long memberId, HttpServletRequest request) {

        HttpSession session = request.getSession();

        Long nowMemberId = (Long) session.getAttribute("memberID");

        BlockModel outputBlock = blockManageService.saveBlockMember(nowMemberId,memberId);
        logger.info("[END] GroupManageController postBlock");
        return new ResultMessageModel(
                "S0001",
                "[SUCCESS]:postBlock|" + outputBlock.toString()
        );
    }

    /**
     * 해당 맴버의 차단 전체 조회
     * @param request
     * @return
     */
    @PostMapping("/finds")
    @ResponseBody
    public ResultMessageModel postFindAll(HttpServletRequest request) {

        HttpSession session = request.getSession();
        Long nowMemberId = (Long) session.getAttribute("memberID");

        List<BlockModel> outputBlock = blockManageService.findAll(nowMemberId);
        logger.info("[END] GroupManageController postBlock");
        return new ResultMessageModel(
                "S0001",
                "[SUCCESS]:postBlock|" + outputBlock.toString()
        );
    }

    public boolean checkForSession(){

        return false;
    }
}
