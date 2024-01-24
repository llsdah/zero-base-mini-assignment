package com.example.zerobase.zerobaseminiassignment.controller;

import com.example.zerobase.zerobaseminiassignment.common.ResultMessageUtil;
import com.example.zerobase.zerobaseminiassignment.model.LinkModel;
import com.example.zerobase.zerobaseminiassignment.model.MemberModel;
import com.example.zerobase.zerobaseminiassignment.model.ResultMessageModel;
import com.example.zerobase.zerobaseminiassignment.service.GroupManageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/group")
public class GroupManageController {

    @Autowired
    private GroupManageService groupManageService;

    /**
     * 참여자 초대
     * ✅ 그룹 매니저는 그룹에 참여자를 초대할 수 있습니다.
     * ✅ 회원 초대 시 DB에 임시 회원을 생성하고 초대 링크를 생성 합니다.
     * ✅ 생성 시에 회원의 이름, 전화번호, 이메일 주소는 필수 값입니다.
     * @param memberModel
     * @return ResultMessageModel
     */
    // 로그인 정보
    @PostMapping("/invite")
    @ResponseBody
    public ResultMessageModel postInvite(@RequestBody MemberModel memberModel){
        log.info("[START] GroupManageController postInvite");

        LinkModel result = groupManageService.invite(memberModel);
        log.info("[END] GroupManageController postInvite");
        if(result != null){
            return ResultMessageUtil.success("postInvite", result);
        }else {
            return ResultMessageUtil.fail();
        }

    }

    /**
     * 참여 수락
     * ✅ 초대 받은 사용자는 그룹 참여 초대 링크를 통해 그룹에 참여할 수 있습니다.
     * ✅ 초대 링크 수락 시 임시 회원을 활성화하고 초대 링크를 만료합니다.
     * ✅ 초대 링크는 1회 사용 시 만료됩니다.
     * @param link
     * @return ResultMessageModel
     */
    @PostMapping("/accept")
    @ResponseBody
    public ResultMessageModel postAccept(@RequestBody LinkModel link){
        log.info("[START] GroupManageController postAccept");

        MemberModel result = groupManageService.accept(link);

        log.info("[END] GroupManageController postAccept");
        if(result != null){
            return ResultMessageUtil.success("postAccept", result);
        }else {
            return ResultMessageUtil.fail();
        }
    }

}
