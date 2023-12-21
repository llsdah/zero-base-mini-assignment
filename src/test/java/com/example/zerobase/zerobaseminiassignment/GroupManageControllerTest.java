package com.example.zerobase.zerobaseminiassignment;

import com.example.zerobase.zerobaseminiassignment.common.MemberUtil;
import com.example.zerobase.zerobaseminiassignment.model.MemberModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
public class GroupManageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private static final MockHttpSession mockHttpSession = new MockHttpSession();

    @BeforeEach
    @DisplayName("세션 추가")
    void beforeEach() throws Exception {
        Long number =0L;

        MemberModel managerMember = new MemberModel("Manager","010-1234-5678","manager@manager.com","매니저", number);
        ObjectMapper objectMapper = new ObjectMapper();

        String returnBody = "{\"messageCode\":\"S0001\",\"messageContent\":\"[SUCCESS]:postCreate|true\"}";
        // 관리자 세션 추가
        String result = mockMvc.perform(MockMvcRequestBuilders.post("/member/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(managerMember)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .json(returnBody))
                .andReturn().getResponse().getContentAsString();

        // 요청 결과에 따라 추가 로직 수행
        if (result.contains(returnBody)) {
            mockHttpSession.setAttribute(MemberUtil.MANAGER,true);
        }
    }

    @Test
    @DisplayName("그룹 초대 기능 테스트")
    void postInviteTest() throws Exception {

        Long number = 0L;
        MemberModel etcMember = new MemberModel("Test","010-1234-5678","test@test.com","", number);
        ObjectMapper objectMapper = new ObjectMapper();

        // 그룹 초대 링크 생성
        mockMvc.perform(MockMvcRequestBuilders.post("/group/invite")
                        .session(mockHttpSession)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(etcMember)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .json("{\"messageCode\":\"S0001\",\"messageContent\":\"[SUCCESS]:postInvite\"}"))
                .andDo(print());
    }

    @Test
    @DisplayName("그룹 초대 수락 기능 테스트")
    void postAcceptTest() throws Exception {

        Long number = 1L;
        MemberModel memberModel = new MemberModel("test","test010","test@","관리자", number);
        String linkModel =         "{\"linkId\":\"1\",\"memberId\":\"1\"}";

        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.post("/group/invite")
                        .session(mockHttpSession)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                memberModel
                        )))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .json("{\"messageCode\":\"S0001\",\"messageContent\":\"[SUCCESS]:postInvite\"}"))
                .andDo(print());

        mockMvc.perform(MockMvcRequestBuilders.post("/group/accept")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                linkModel
                        ))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .json("{\"messageCode\":\"S0001\",\"messageContent\":\"[SUCCESS]:postAccept\"}"))
                .andDo(print());

    }
}
