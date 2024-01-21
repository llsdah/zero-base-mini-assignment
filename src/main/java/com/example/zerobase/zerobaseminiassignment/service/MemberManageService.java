package com.example.zerobase.zerobaseminiassignment.service;

import com.example.zerobase.zerobaseminiassignment.common.ResultMessageUtil;
import com.example.zerobase.zerobaseminiassignment.model.MemberModel;
import com.example.zerobase.zerobaseminiassignment.model.ResultMessageModel;
import com.example.zerobase.zerobaseminiassignment.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.transform.Result;
import java.util.List;
import java.util.Optional;


@Slf4j
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

    public ResultMessageModel find(Long id){
        log.info("MemberManageService find "+ id);
        Optional<MemberModel> output = memberRepository.findById(id);
        if(output.isPresent()) {
            return ResultMessageUtil.success(output.get());
        };
        return ResultMessageUtil.fail();
    }

    public List<MemberModel> findAll(){
        return memberRepository.findAll();
    }

    public MemberModel getMember(MemberModel memberModel) {
        log.info("memberModel1 : "+memberModel.toString());

        log.info("memberModel2 : "+find(1L));

        Optional<MemberModel> outModel = memberRepository.findByEmailAndPassword(
                memberModel.getEmail(),memberModel.getPassword());

        if(outModel.isPresent()){
            return outModel.get();
        };
        return null;
    }
}
