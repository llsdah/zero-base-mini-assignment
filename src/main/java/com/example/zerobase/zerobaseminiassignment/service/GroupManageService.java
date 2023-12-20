package com.example.zerobase.zerobaseminiassignment.service;

import com.example.zerobase.zerobaseminiassignment.model.DateModel;
import com.example.zerobase.zerobaseminiassignment.model.LinkModel;
import com.example.zerobase.zerobaseminiassignment.model.MemberModel;
import com.example.zerobase.zerobaseminiassignment.repository.LinkRepository;
import com.example.zerobase.zerobaseminiassignment.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class GroupManageService {

    private static final Logger logger = LoggerFactory.getLogger(GroupManageService.class);

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
        logger.info("[START] GroupManageService createInviteLink");

        DateModel dateModel = getData();
        memberModel.setRegistrationDate(dateModel.getRegistrationDate());
        memberModel.setModificationDate(dateModel.getModificationDate());

        MemberModel saveMemberModel = memberRepository.save(memberModel);

        LinkModel createLink = linkRepository.save(new LinkModel(url, title, contents, false , saveMemberModel.getMemberId()));

        createLink.setRegistrationDate(dateModel.getRegistrationDate());
        createLink.setModificationDate(dateModel.getModificationDate());

        logger.debug("link [{}]",createLink.toString());
        logger.info("[START] GroupManageService createInviteLink");
        return createLink;
    }

    /**
     * 그룹 초대 수락에 대한 메소드
     * @param link
     * @return MemberModel
     */

    @Transactional
    public MemberModel accept(LinkModel link) {
        logger.info("[START] GroupManageService acceptMember");
        logger.debug("link [{}]", link.toString());

        //Hospital hospital = hospitalRepository.findById(id)
          //      .orElseThrow(()
        MemberModel memberModel = memberRepository.findById(link.getMemberId())
                .orElseThrow( () -> new RuntimeException("맴버를 찾을 수 없습니다. 맴버 ID : "+link.getMemberId() ));

        if(memberModel!=null){
            //LinkModel modifiedLink = entityManager.find(LinkModel.class, link.getLinkId());
            LinkModel modifiedLink = linkRepository.findById(link.getLinkId())
                    .orElseThrow(() -> new RuntimeException("링크 데이터를 찾을 수 없습니다. 링크 ID : "+link.getLinkId()));
            if (modifiedLink != null && !modifiedLink.isUseFlag()) {
                logger.debug("modify link flag");
                modifiedLink.setUseFlag(true);

                logger.debug("link [{}]", modifiedLink.toString());
            }

        }else{
            logger.debug("member [{ null }]");
        }

        logger.debug("member [{}]", memberModel.toString());
        logger.info("[END] GroupManageService acceptMember");
        return memberModel;
    }

    /**
     * 시간 등록을 위한 메소드
     * @return DateModel
     */
    public DateModel getData(){
        // 한국 시간대로 설정
        ZoneId koreaZone = ZoneId.of("Asia/Seoul");
        ZonedDateTime koreaTime = ZonedDateTime.now(koreaZone);
        LocalDateTime currentTime = koreaTime.toLocalDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String formattedTime = currentTime.format(formatter);

        return new DateModel();
    }
}
