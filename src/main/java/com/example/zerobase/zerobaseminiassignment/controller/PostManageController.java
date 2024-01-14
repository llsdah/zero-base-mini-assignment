package com.example.zerobase.zerobaseminiassignment.controller;

import com.example.zerobase.zerobaseminiassignment.common.MemberUtil;
import com.example.zerobase.zerobaseminiassignment.model.BlockModel;
import com.example.zerobase.zerobaseminiassignment.model.MemberModel;
import com.example.zerobase.zerobaseminiassignment.model.PostModel;
import com.example.zerobase.zerobaseminiassignment.model.ResultMessageModel;
import com.example.zerobase.zerobaseminiassignment.service.BlockManageService;
import com.example.zerobase.zerobaseminiassignment.service.MemberManageService;
import com.example.zerobase.zerobaseminiassignment.service.PostManageService;
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
@RequestMapping("/post")
public class PostManageController {

    private static final Logger logger = LoggerFactory.getLogger(PostManageController.class);

    @Autowired
    private PostManageService postManageService;

    @PostMapping("/create")
    @ResponseBody
    public ResultMessageModel postCreate(@RequestBody PostModel postModel, HttpServletRequest request){
        logger.info("[START] PostManageController postCreate");

        //HttpSession session = request.getSession();
        PostModel outputPost = postManageService.create(postModel);

        if(outputPost == null){
            return new ResultMessageModel(
                    "E0001",
                    "[NULL]:PostModel",
                    outputPost

            );
        }

        logger.info("outputMember : [{}]",outputPost.toString());
        logger.info("[END] PostManageController postCreate");
        return new ResultMessageModel(
                "S0001",
                "[SUCCESS]:postCreate",
                outputPost
        );
    }

    /**
     * 게시글 조회하기 단, 차단된 계시글을 제회하고
     * @param postId
     * @return
     */
    @PostMapping("/find")
    @ResponseBody
    public ResultMessageModel postFind(@RequestBody Long postId){
        logger.info("[START] PostManageController postFind");

        PostModel outputPost = postManageService.find(postId);

        if(outputPost == null){
            return new ResultMessageModel(
                    "E0001",
                    "[NULL]:PostModel",
                    outputPost
            );
        }

        logger.info("outputMember : [{}]",outputPost.toString());
        logger.info("[END] PostManageController postCreate");
        return new ResultMessageModel(
                "S0001",
                "[SUCCESS]:postFind ",
                outputPost
        );
    }

    @PostMapping("/finds")
    @ResponseBody
    public ResultMessageModel postFindAll(HttpServletRequest request){
        logger.info("[START] PostManageController postFind");

        HttpSession session = request.getSession();
        Long nowMemberId = (Long) session.getAttribute("memberID");

        List<PostModel> outputPost = postManageService.findAll(nowMemberId);

        if(outputPost == null){
            return new ResultMessageModel(
                    "E0001",
                    "[NULL]:PostModel",
                    outputPost
            );
        }

        logger.info("outputMember : [{}]",outputPost.toString());
        logger.info("[END] PostManageController postCreate");
        return new ResultMessageModel(
                "S0001",
                "[SUCCESS]:postFind ",
                outputPost
        );
    }
    // 테스트용 코드
    @PostMapping("/find/title")
    @ResponseBody
    public ResultMessageModel postFindTitle(@RequestBody String title){
        logger.info("[START] PostManageController postFindTitle");

        //HttpSession session = request.getSession();

        List<PostModel> outputPost = postManageService.findByTitle(title);

        if(outputPost == null){
            return new ResultMessageModel(
                    "E0001",
                    "[NULL]:PostModel",
                    outputPost
            );
        }

        logger.info("outputMember : [{}]",outputPost.toString());
        logger.info("[END] PostManageController postCreate");
        return new ResultMessageModel(
                "S0001",
                "[SUCCESS]:postFind ",
                outputPost
        );
    }

}
