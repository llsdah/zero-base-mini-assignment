package com.example.zerobase.zerobaseminiassignment.service;

import com.example.zerobase.zerobaseminiassignment.common.MyAuthUtil;
import com.example.zerobase.zerobaseminiassignment.common.MyJwtUtil;
import com.example.zerobase.zerobaseminiassignment.common.ResultMessageUtil;
import com.example.zerobase.zerobaseminiassignment.model.ModificationDateModel;
import com.example.zerobase.zerobaseminiassignment.model.LinkModel;
import com.example.zerobase.zerobaseminiassignment.model.MemberModel;
import com.example.zerobase.zerobaseminiassignment.model.ResultMessageModel;
import com.example.zerobase.zerobaseminiassignment.repository.LinkRepository;
import com.example.zerobase.zerobaseminiassignment.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
public class GroupManageService {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private LinkRepository linkRepository;

    @PersistenceContext
    private EntityManager entityManager;
    @Value("${link.url}")
    private String url;
    @Value("${link.title}")
    private String title;
    @Value("${link.contents}")
    private String contents;

    /**
     * 그룹 초대에 대한 메소드
     * @param memberModel
     * @return LinkModel
     */
    @Transactional
    public LinkModel invite(MemberModel memberModel) {

        if (!StringUtils.hasText(memberModel.getUsername())
            || !StringUtils.hasText(memberModel.getPhoneNumber())
            || !StringUtils.hasText(memberModel.getEmail())
            ){

            throw new RuntimeException("member model data is null");
        }

        ModificationDateModel modificationDateModel = getData();
        memberModel.setRegistrationDate(modificationDateModel.getRegistrationDate());
        memberModel.updateModificationDate(modificationDateModel.getModificationDate());
        memberModel.updateAuthority(MyAuthUtil.TEMPORARY_USER);

        MemberModel savedMemberModel = memberRepository.save(memberModel);
        LinkModel createdLink = linkRepository.save(new LinkModel(url, title, contents, false , savedMemberModel.getMemberId()));

        createdLink.setRegistrationDate(modificationDateModel.getRegistrationDate());
        createdLink.updateModificationDate(modificationDateModel.getModificationDate());

        return createdLink;
    }

    /**
     * 그룹 초대 수락에 대한 메소드
     * @param link
     * @return MemberModel
     */
    @Transactional
    public MemberModel accept(LinkModel link) {

        MemberModel memberModel = memberRepository.findById(link.getMemberId())
                .orElseThrow( () -> new RuntimeException("맴버를 찾을 수 없습니다. 맴버 ID : "+link.getMemberId() ));

        if(memberModel!=null){

            LinkModel modifiedLink = linkRepository.findById(link.getLinkId())
                    .orElseThrow(() -> new RuntimeException("링크 데이터를 찾을 수 없습니다. 링크 ID : "+link.getLinkId()));
            if (modifiedLink != null && !modifiedLink.isUseFlag()) {
                memberModel.updateAuthority(MyAuthUtil.USER);
                modifiedLink.updateUseFlag(true);
            }

        }

        return memberModel;
    }

    /**
     * 시간 등록을 위한 메소드
     * @return DateModel
     */
    public ModificationDateModel getData(){
        // 한국 시간대로 설정
        ZoneId koreaZone = ZoneId.of("Asia/Seoul");
        ZonedDateTime koreaTime = ZonedDateTime.now(koreaZone);
        LocalDateTime currentTime = koreaTime.toLocalDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String formattedTime = currentTime.format(formatter);

        return new ModificationDateModel();
    }
}
