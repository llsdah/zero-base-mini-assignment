package com.example.zerobase.zerobaseminiassignment.service;

import com.example.zerobase.zerobaseminiassignment.common.MyJwtUtil;
import com.example.zerobase.zerobaseminiassignment.model.FollowModel;
import com.example.zerobase.zerobaseminiassignment.model.MemberModel;
import com.example.zerobase.zerobaseminiassignment.repository.FollowRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FollowManageService {

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private MemberManageService memberManageService;
    public boolean save(Long memberId) {

        try{
            MemberModel followerMember = memberManageService.find(memberId);
            followRepository.save(new FollowModel(MyJwtUtil.getMember(),followerMember));
        }catch (Exception e){
            log.error(e.getMessage());
            return false;
        }
        return true;
    }

    public boolean delete(Long memberId) {

        try{
            MemberModel followerMember = memberManageService.find(memberId);
            followRepository.deleteByFollowerMemberIdAndFollowingMemberId
                    (MyJwtUtil.getMember(),followerMember);
        }catch (Exception e){
            log.error(e.getMessage());
            return false;
        }
        return true;
    }
}
