package com.example.zerobase.zerobaseminiassignment;

import com.example.zerobase.zerobaseminiassignment.model.MemberModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
public class GroupManageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("그룹 초대 기능 테스트")
    void postInviteTest() throws Exception {

        Long number = 0L;
        MemberModel memberModel = new MemberModel("test","test010","test@","관리자", number);
        //Member member = new Member();
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.post("/group/invite")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberModel)))
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

        //Member member = new Member();
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.post("/group/invite")
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
