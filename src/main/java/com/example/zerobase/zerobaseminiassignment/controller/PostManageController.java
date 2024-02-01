package com.example.zerobase.zerobaseminiassignment.controller;

import com.example.zerobase.zerobaseminiassignment.common.ResultMessageUtil;
import com.example.zerobase.zerobaseminiassignment.model.PostModel;
import com.example.zerobase.zerobaseminiassignment.model.ResultMessageModel;
import com.example.zerobase.zerobaseminiassignment.service.LikePostMenageService;
import com.example.zerobase.zerobaseminiassignment.service.PostManageService;
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
        return ResultMessageUtil.resultMessage("S0001","게시글을 생성했습니다.",result);
    }

    /**
     * 게시글 조회하기
     * @param postId
     * @return
     */
    @GetMapping("/{postId}")
    @ResponseBody
    public ResultMessageModel getPost(@PathVariable("postId") Long postId){
        log.info("[START] PostManageController getPost");

        PostModel result = postManageService.find(postId);

        log.info("[END] PostManageController getPost");
        return ResultMessageUtil.resultMessage("S0001","게시글을 조회했습니다.",result);
    }

    /**
     * 게시글 전체 조회, 단 차단한 맴버 혹은 게시글 제외하고
     * @return
     */
    @GetMapping("/all")
    @ResponseBody
    public ResultMessageModel getPosts(){
        log.info("[START] PostManageController getPosts");
        // 몇개씩 볼지 그리고 페이지 번호
        List<PostModel> result = postManageService.findPostByPageIndex(10,1);

        log.info("[END] PostManageController getPosts");
        return ResultMessageUtil.resultMessage("S0001","게시글을 전체 조회 했습니다.",result);
    }

    @PutMapping("/{postId}")
    @ResponseBody
    public ResultMessageModel updatePost(@PathVariable("postId")Long postId, PostModel postModel){
        log.info("[START] PostManageController updatePost");

        PostModel result = postManageService.update(postId, postModel);

        log.info("[END] PostManageController updatePost");
        return ResultMessageUtil.resultMessage("S0001","게시글을 수정했습니다.",result);
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
        return ResultMessageUtil.resultMessage("S0001","게시글을 삭제했습니다.",result);
    }

    /**
     * 게시글 좋아요
     * @param postId
     * @return
     */
    @PostMapping("/like/{postId}")
    @ResponseBody
    public ResultMessageModel postLikePosts(@PathVariable("postId")Long postId){
        log.info("[START] PostManageController postLikePosts");

        boolean result = likePostMenageService.save(postId);

        log.info("[END] PostManageController postLikePosts");
        return ResultMessageUtil.resultMessage("S0001","게시글을 '좋아요'했습니다.",result);
    }

    /**
     * 게시글 좋아요 취소
     * @param postId
     * @return
     */
    @DeleteMapping("/like/{postId}")
    @ResponseBody
    public ResultMessageModel deleteLikePosts(@PathVariable("postId") Long postId){
        log.info("[START] PostManageController deleteLikePosts");

        boolean result = likePostMenageService.delete(postId);

        log.info("[END] PostManageController deleteLikePosts");
        return ResultMessageUtil.resultMessage("S0001","게시글의 '좋아요'를 취소했습니다.",result);
    }

    /**
     * 게시글을 제목으로 검색
     * @param title
     * @return
     */
    @GetMapping("/title")
    @ResponseBody
    public ResultMessageModel getPostFilterByTitle(String title){
        log.info("[START] PostManageController postFindTitle");

        List<PostModel> result = postManageService.getPostFilterByTitle(title);

        log.info("[END] PostManageController postFindTitle");
        return ResultMessageUtil.resultMessage("S0001",title+" 조건의 검색 결과입니다.",result);
    }

    /**
     * 게시글을 해시 태그 검색
     * @param hashTag
     * @return
     */
    @GetMapping("/hashtag")
    @ResponseBody
    public ResultMessageModel getPostFilterByHashTag(String hashTag){
        log.info("[START] PostManageController getPostFilterByHashTag " + hashTag);

        List<PostModel> result = postManageService.getPostFilterByHashTag(hashTag);

        log.info("[END] PostManageController getPostFilterByHashTag");
        return ResultMessageUtil.resultMessage("S0001",hashTag+" 조건의 검색 결과입니다.",result);
    }

}
