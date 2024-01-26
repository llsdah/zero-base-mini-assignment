package com.example.zerobase.zerobaseminiassignment.service;

import com.example.zerobase.zerobaseminiassignment.common.MyJwtUtil;
import com.example.zerobase.zerobaseminiassignment.model.FollowModel;
import com.example.zerobase.zerobaseminiassignment.model.MemberModel;
import com.example.zerobase.zerobaseminiassignment.repository.FollowRepository;
import com.example.zerobase.zerobaseminiassignment.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class FollowManageService {

    @Autowired
    private FollowRepository followRepository;
    @Autowired
    private MemberRepository memberRepository;

    public boolean save(Long memberId) {

        try{
            Optional<MemberModel> followerMember = memberRepository.findById(memberId);
            if(followerMember.isEmpty()){
                return false;
            }
            followRepository.save(new FollowModel(MyJwtUtil.getMember(),followerMember.get()));
        }catch (Exception e){
            log.error(e.getMessage());
            return false;
        }
        return true;
    }

    public boolean delete(Long memberId) {

        try{
            Optional<MemberModel> followerMember = memberRepository.findById(memberId);
            if(followerMember.isEmpty()) return false;
            followRepository.deleteByFollowerMemberIdAndFollowingMemberId
                    (MyJwtUtil.getMember(),followerMember.get());
        }catch (Exception e){
            log.error(e.getMessage());
            return false;
        }
        return true;
    }
}
