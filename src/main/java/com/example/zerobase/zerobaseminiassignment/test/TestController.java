package com.example.zerobase.zerobaseminiassignment.test;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/aaa")
public class TestController {

    @PostMapping("/test")
    public String postTest(@RequestBody String str){
        String jsonInputString = String.format("{\"key1\": \"value1\", \"key2\": \"%s\"}", str);

        return jsonInputString;
    }
    @PostMapping("/local")
    public String test(@RequestBody String str) {
        System.out.println("test 중인데 안되는거 ㅏㅌ다. ");
        String result = "";
        try {
            // HttpClient 생성
            HttpClient httpClient = HttpClients.createDefault();
            // POST 요청 생성
            String url = "http://localhost:8080/crypt";
            HttpPost httpPost = new HttpPost(url);
            // JSON 데이터 생성
            String jsonInputString = String.format("{\"key1\": \"value1\", \"key2\": \"%s\"}",str);

            // JSON 데이터를 StringEntity로 설정
            StringEntity stringEntity = new StringEntity(jsonInputString);
            httpPost.setEntity(stringEntity);

            // 헤더 설정 (JSON 형식으로 데이터 전송)
            httpPost.setHeader("Content-type", "application/json");
            System.out.println("호출은 하는것 같습니다.");
            // POST 요청 전송 및 응답 수신
            HttpResponse response = httpClient.execute(httpPost);

            int statius = response.getStatusLine().getStatusCode();
            System.out.println("status : "+statius);
            // 응답 데이터 처리
            HttpEntity responseEntity = response.getEntity();

            String responseString ="";

            if (responseEntity != null) {
                responseString = EntityUtils.toString(responseEntity);
                System.out.println("controller Response: " + responseString);
            }

            ObjectMapper objectMapper = new ObjectMapper();

            // JSON 문자열을 JsonNode로 파싱
            JsonNode jsonNode = objectMapper.readTree(responseString);
            System.out.println("뭐지 여긴가>?");
            boolean flag1 = jsonNode.get("key").asBoolean();
            if(flag1){
                System.out.println("key true");
            }else{
                System.out.println("key false");
            }
            String keyTest = jsonNode.get("key").asText();

            System.out.println("keyTest : "+keyTest);

            result = keyTest;

        } catch (Exception e) {
            e.getMessage();
        }
        return result;
    }
}
