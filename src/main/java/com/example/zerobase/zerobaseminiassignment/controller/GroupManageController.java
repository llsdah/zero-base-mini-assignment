package com.example.zerobase.zerobaseminiassignment.controller;

import com.example.zerobase.zerobaseminiassignment.common.MemberUtil;
import com.example.zerobase.zerobaseminiassignment.model.LinkModel;
import com.example.zerobase.zerobaseminiassignment.model.MemberModel;
import com.example.zerobase.zerobaseminiassignment.model.ResultMessageModel;
import com.example.zerobase.zerobaseminiassignment.service.GroupManageService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/group")
public class GroupManageController {

    private static final Logger logger = LoggerFactory.getLogger(GroupManageController.class);

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
    public ResultMessageModel postInvite(@RequestBody MemberModel memberModel, HttpServletRequest request){
        logger.info("[START] GroupManageController postInvite");

        // 매니저 확인
        HttpSession session = request.getSession();
        if(session.getAttribute(MemberUtil.MANAGER) == null || !(boolean)session.getAttribute(MemberUtil.MANAGER) ){
            return new ResultMessageModel(
                    "E0003",
                    "[AUTH]:Need Authority"
            );
        }

        // 필수 값 확인
        if(memberModel == null) {
            return new ResultMessageModel(
                    "E0001",
                    "[NULL]:MemberModel"
            );
        }else if (!StringUtils.hasText(memberModel.getName())
                    || !StringUtils.hasText(memberModel.getPhoneNumber())
                    || !StringUtils.hasText(memberModel.getEmail())
        ){
                return new ResultMessageModel(
                        "E0002",
                        "[NULL]:MemberModel essential parameter"
                );
        }

        LinkModel outputLink = groupManageService.invite(memberModel);

        if(outputLink == null){
            return new ResultMessageModel(
                    "E0001",
                    "[NULL]:LinkModel"
            );
        }

        logger.info("outputLink : [{}]",outputLink.toString());
        logger.info("[END] GroupManageController postInvite");
        return new ResultMessageModel(
                "S0001",
                "[SUCCESS]:postInvite"
        );
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
        logger.info("[START] GroupManageController postAccept");

        MemberModel outputMemberModel = groupManageService.accept(link);

        if(outputMemberModel==null){
            return new ResultMessageModel(
                    "E0001",
                    "[NULL]:MemberModel"
            );
        }

        logger.info("[END] GroupManageController postAccept");
        return new ResultMessageModel(
                "S0001",
                "[SUCCESS]:postAccept"
        );
    }

}
