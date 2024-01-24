package com.example.zerobase.zerobaseminiassignment.controller;

import com.example.zerobase.zerobaseminiassignment.common.ResultMessageUtil;
import com.example.zerobase.zerobaseminiassignment.model.MemberModel;
import com.example.zerobase.zerobaseminiassignment.model.PostModel;
import com.example.zerobase.zerobaseminiassignment.model.ResultMessageModel;
import com.example.zerobase.zerobaseminiassignment.service.LikePostMenageService;
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

    @Autowired
    private LikePostMenageService likePostMenageService;

    @PostMapping
    @ResponseBody
    public ResultMessageModel postCreate(@RequestBody PostModel postModel){
        log.info("[START] PostManageController postCreate");

        PostModel result = postManageService.save(postModel);

        log.info("[END] PostManageController postCreate");
        if(result != null){
            return ResultMessageUtil.success("postCreate", result);
        }
        return ResultMessageUtil.fail();
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

        PostModel result = postManageService.find(postId);

        log.info("[END] PostManageController postCreate");
        if(result != null){
            return ResultMessageUtil.success("getFind", result);
        }

        return ResultMessageUtil.fail();

    }

    /**
     * 게시글 전체 조회, 단 차단한 맴버 혹은 게시글 제외하고
     * @return
     */
    @GetMapping("/all")
    @ResponseBody
    public ResultMessageModel getPosts(){
        log.info("[START] PostManageController getPosts");

        List<PostModel> result = postManageService.findAll();

        log.info("[END] PostManageController getPosts");

        if(result != null){
            return ResultMessageUtil.success("getPosts", result);
        }

        return ResultMessageUtil.fail();
    }

    @PutMapping("/{postId}")
    @ResponseBody
    public ResultMessageModel updatePost(@PathVariable("postId")Long postId, PostModel postModel){
        log.info("[START] PostManageController updatePost");

        PostModel result = postManageService.update(postId, postModel);

        log.info("[END] PostManageController updatePost");
        if(result != null){
            return ResultMessageUtil.success("updatePost", result);
        }
        return ResultMessageUtil.fail();
    }

    /**
     * 게시글 삭제.
     * @return
     */
    @DeleteMapping("/{postId}")
    @ResponseBody
    public ResultMessageModel deletePost(@PathVariable("postId")Long postId){
        log.info("[START] PostManageController deletePost");

        boolean result = postManageService.delete(postId);

        log.info("[END] PostManageController deletePost");
        if(result){
            return ResultMessageUtil.success("deletePost", result);
        }
        return ResultMessageUtil.fail();
    }

    @PostMapping("/like/{postId}")
    @ResponseBody
    public ResultMessageModel postLikePosts(@PathVariable("postId")Long postId){
        log.info("[START] PostManageController postLikePosts");

        boolean result = likePostMenageService.save(postId);

        log.info("[END] PostManageController postLikePosts");
        if(result){
            return ResultMessageUtil.success("postLikePosts", result);
        }

        return ResultMessageUtil.fail();

    }

    @DeleteMapping("/like/{postId}")
    @ResponseBody
    public ResultMessageModel deleteLikePosts(@PathVariable("postId")Long postId){
        log.info("[START] PostManageController postLikePosts");

        boolean result = likePostMenageService.delete(postId);

        log.info("[END] PostManageController deleteLikePosts");
        if(result){
            return ResultMessageUtil.success("deleteLikePosts", result);
        }

        return ResultMessageUtil.fail();

    }

    /**
     * 향후 검색 기능을 위한 메소드
     * @param title
     * @return
     */
    @PostMapping("/find/title")
    @ResponseBody
    public ResultMessageModel postFindTitle(@RequestBody String title){
        log.info("[START] PostManageController postFindTitle");

        List<PostModel> result = postManageService.findByTitle(title);

        log.info("[END] PostManageController postCreate");
        if(result != null){
            return ResultMessageUtil.success("postFindTitle", result);
        }

        return ResultMessageUtil.fail();

    }

}
