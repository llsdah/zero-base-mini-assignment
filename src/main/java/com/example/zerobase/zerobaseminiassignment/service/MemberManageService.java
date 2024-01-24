package com.example.zerobase.zerobaseminiassignment.service;

import com.example.zerobase.zerobaseminiassignment.common.*;
import com.example.zerobase.zerobaseminiassignment.model.*;
import com.example.zerobase.zerobaseminiassignment.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Slf4j
@Service
public class MemberManageService {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MyJwtProvider myJwtProvider;
    @PersistenceContext
    private EntityManager entityManager;
    /**
     * 샘플 멤버 생성을 위한 메소드
     * @param memberModel
     * @return MemberModel
     */
    public MemberModel save(MemberModel memberModel) {
        log.info("MemberManageService save");
        MemberModel model = null;
        try {
            model = memberRepository.save(memberModel);

        } catch (DataIntegrityViolationException e) {
            // 데이터베이스 제약 조건 등에 위배되어 저장 실패
            // 적절한 예외 처리를 수행
            log.error(e.getMessage());
        }

        return model;
    }

    /**
     * 데이터가 없다면 null이 맞을까?
     * @return
     */
    
    public MemberModel find(){
        log.info("MemberManageService find ");
        MemberModel result = null;
        Optional<MemberModel> output = memberRepository.findById(MyJwtUtil.getMemberId());
        if(output.isPresent()) {
            result = output.get();
        };

        return result;
    }

    public MemberModel find(Long id){
        log.info("MemberManageService find if"+ id);
        MemberModel result = null;
        Optional<MemberModel> output = memberRepository.findById(id);
        if(output.isPresent()) {
            result = output.get();
        };

        return result;
    }

    public List<MemberModel> findAll(){
        log.info("MemberManageService findAll");
        List<MemberModel> memberModelList = memberRepository.findAll();
        log.info("MemberManageService : ");
        if(!memberModelList.isEmpty()){
            // 특정 맴버 제외 로직이 추가 되어야 할까?, 특정 관리자일 경우는?
            for(MemberModel memberModel: memberModelList){
                // 잠시 보류
            }
        }

        return memberModelList;
    }

    /**
     * 단순멤버 체크용도
     * @param memberModel
     * @return
     */
    public String checkLoginMember(MemberModel memberModel) {
        log.info("MemberManageService checkLoginMember");

        Optional<MemberModel> outputMember = memberRepository.findByEmailAndPassword(
                memberModel.getEmail(),memberModel.getPassword());
        String token = "";

        if(outputMember.isPresent()){
            token = myJwtProvider.generateToken(outputMember.get());
            return token;
        }

        log.info("token : "+token);
        return token;
    }

    @Transactional
    public MemberModel update(Long memberId, MemberModel memberModel) {
        log.info("MemberManageService update");
        MemberModel existingData = null;
        try {
            existingData = entityManager.find(MemberModel.class, memberId);;

            // 기존 데이터데 수정된 데이터가 있으면 붙영 넣고 다시 저장.
            if(memberModel.getAuthority() != null){
                existingData.updateAuthority(memberModel.getAuthority());
            }
            if(memberModel.getStatus() != null){
                existingData.updateStatus(memberModel.getStatus());
            }
            if(memberModel.getPhoneNumber() != null){
                existingData.setPhoneNumber(memberModel.getPhoneNumber());
            }
            if(memberModel.getUsername() != null){
                existingData.setUsername(memberModel.getUsername());
            }
            if(memberModel.getPassword() != null){
                existingData.setPassword(memberModel.getPassword());
            }

            existingData.updateModificationDate(MyDateUtil.getData().getModificationDate());

            entityManager.merge(existingData);
        } catch (DataIntegrityViolationException e) {
            // 데이터베이스 제약 조건 등에 위배되어 저장 실패
            // 적절한 예외 처리를 수행
            log.error(e.getMessage());
        }

        return existingData;

    }

    /**
     * 맴버 삭제
     * ✅ 삭제에 대한 권한 체크  ->  본인 스스로인 경우-> (탈퇴 요청)
     * ✅ 타 참조 테이블 확인해 같이 넣어 주기 : 신고x, 차단0, 게시글0
     * @param memberId
     * @return
     */
    public boolean delete(Long memberId) {
        if(!MyJwtUtil.checkAuth(MyAuthUtil.MANAGER)){
            log.error("need Authority");
            return false;
        }

        try {
            //별도 구현
            BlockManageService blockManageService = new BlockManageService();
            PostManageService postManageService = new PostManageService();
            ReportManageService reportManageService = new ReportManageService();

            Optional<MemberModel> deleteData = memberRepository.findById(memberId);

            if (deleteData.isPresent()){

                // 신고 와 차단 받은 사람만 남겨 주기 .
                blockManageService.deleteByBlockerMember(deleteData.get());
                postManageService.deleteByMemberId(deleteData.get());
                reportManageService.deleteByTargetMemberId(deleteData.get());

                memberRepository.deleteById(memberId);
            }

        } catch (DataIntegrityViolationException e) {
            // 데이터베이스 제약 조건 등에 위배되어 저장 실패
            // 적절한 예외 처리를 수행
            log.error(e.getMessage());
            return false;
        }

        return true;
    }
}
