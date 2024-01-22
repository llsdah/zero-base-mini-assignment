package com.example.zerobase.zerobaseminiassignment.controller;

import com.example.zerobase.zerobaseminiassignment.model.PostModel;
import com.example.zerobase.zerobaseminiassignment.model.ResultMessageModel;
import com.example.zerobase.zerobaseminiassignment.service.PostManageService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/posts")
public class PostManageController {

    @Autowired
    private PostManageService postManageService;

    @PostMapping
    @ResponseBody
    public ResultMessageModel postCreate(@RequestBody PostModel postModel){
        log.info("[START] PostManageController postCreate");

        ResultMessageModel result = postManageService.save(postModel);

        if(result.getMessageCode().startsWith("S")){
            result.setMessageContent("[SUCCESS]:postCreate");
        }else {
            result.setMessageContent("[FAIL]:postCreate");
        }

        log.info("[END] PostManageController postCreate");
        return result;
    }

    /**
     * 게시글 조회하기
     * @param postId
     * @return
     */
    @GetMapping("/{postId}")
    @ResponseBody
    public ResultMessageModel getPost(@PathVariable("postId") Long postId){
        log.info("[START] PostManageController postFind");

        ResultMessageModel result = postManageService.find(postId);

        if(result.getMessageCode().startsWith("S")){
            result.setMessageContent("[SUCCESS]:postCreate");
        }else {
            result.setMessageContent("[FAIL]:postCreate");
        }

        log.info("[END] PostManageController postCreate");
        return result;
    }

    /**
     * 게시글 전체 조회, 단 차단한 맴버 혹은 게시글 제외하고
     * @return
     */
    @GetMapping("/all")
    @ResponseBody
    public ResultMessageModel getPosts(){
        log.info("[START] PostManageController getPosts");

        ResultMessageModel result = postManageService.findAll();

        if(result.getMessageCode().startsWith("S")){
            result.setMessageContent("[SUCCESS]:getPosts");
        }else {
            result.setMessageContent("[FAIL]:getPosts");
        }

        log.info("[END] PostManageController getPosts");
        return result;
    }

    // 테스트용 코드
    @PostMapping("/find/title")
    @ResponseBody
    public ResultMessageModel postFindTitle(@RequestBody String title){
        log.info("[START] PostManageController postFindTitle");


        List<PostModel> outputPost = postManageService.findByTitle(title);

        if(outputPost == null){
            return new ResultMessageModel(
                    "E0001",
                    "[NULL]:PostModel",
                    outputPost
            );
        }

        log.info("outputMember : [{}]",outputPost.toString());
        log.info("[END] PostManageController postCreate");
        return new ResultMessageModel(
                "S0001",
                "[SUCCESS]:postFind ",
                outputPost
        );
    }

}
