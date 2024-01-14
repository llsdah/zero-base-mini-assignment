package com.example.zerobase.zerobaseminiassignment.test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;

public class TestServletCall extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(TestServletCall.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 수신된 JSON 데이터 읽기
        BufferedReader reader = request.getReader();
        StringBuilder jsonInput = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonInput.append(line);
        }

        // 수신된 JSON 데이터 출
        ObjectMapper objectMapper = new ObjectMapper();

        // JSON 문자열을 JsonNode로 파싱
        JsonNode jsonNode = objectMapper.readTree(jsonInput.toString());

        // "key"의 값을 가져오기
        String keyValue = "bad";
        if(StringUtils.hasText(jsonNode.get("key2").asText()) &&jsonNode.get("key2").asText().equals("abc")){
            keyValue = "good";
        }

        // 수신된 JSON 데이터를 가공하거나 필요에 따라 다른 로직 수행
        // JSON 형식으로 응답
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // 응답 데이터 생성 (간단한 예제로 "success" 메시지를 담은 JSON 응답)
        String jsonResponse = String.format("{\"status\":\"success\",\"key\":\"%s\"}",keyValue);
        response.getWriter().write(jsonResponse);
    }
}
