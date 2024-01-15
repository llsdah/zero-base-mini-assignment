package com.example.zerobase.zerobaseminiassignment.service;

import com.example.zerobase.zerobaseminiassignment.model.MemberModel;
import com.example.zerobase.zerobaseminiassignment.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberManageService {

    @Autowired
    private MemberRepository memberRepository;

    /**
     * 샘플 멤버 생성을 위한 메소드
     * @param memberModel
     * @return MemberModel
     */
    public MemberModel save(MemberModel memberModel) {

        return memberRepository.save(memberModel);
    }

    public MemberModel find(Long id){

        boolean flag = memberRepository.findById(id).isPresent();
        if(!flag) return null;
        return memberRepository.findById(id).get();
    }

    public List<MemberModel> findAll(){
        return memberRepository.findAll();
    }

}
