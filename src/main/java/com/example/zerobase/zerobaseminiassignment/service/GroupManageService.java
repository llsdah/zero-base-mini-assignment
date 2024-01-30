package com.example.zerobase.zerobaseminiassignment.service;

import com.example.zerobase.zerobaseminiassignment.common.MyAuthorityUtil;
import com.example.zerobase.zerobaseminiassignment.model.LinkModel;
import com.example.zerobase.zerobaseminiassignment.model.MemberModel;
import com.example.zerobase.zerobaseminiassignment.repository.LinkRepository;
import com.example.zerobase.zerobaseminiassignment.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
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

        memberModel.updateAuthority(MyAuthorityUtil.TEMPORARY_USER);

        MemberModel savedMemberModel = memberRepository.save(memberModel);
        LinkModel createdLink = linkRepository.save(new LinkModel(url, title, contents, false , savedMemberModel.getMemberId()));

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
                memberModel.updateAuthority(MyAuthorityUtil.USER);
                modifiedLink.updateUseFlag(true);
            }

        }

        return memberModel;
    }

}
