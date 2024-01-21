package com.example.zerobase.zerobaseminiassignment.test;

import com.example.zerobase.zerobaseminiassignment.model.MemberModel;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
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

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

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

    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public static void main(String[] args) {
        String str = null;
        boolean ttt = Optional.ofNullable(str).isPresent();

        System.out.println("ttt : "+ ttt);
        Date date1 = new Date();
        date1.setTime(System.currentTimeMillis()+600000);
        System.out.println("isTokenExpired : "+ date1.toString());
        System.out.println("isTokenExpired : "+ date1.before(new Date()));


        // 안전한 키 생성
        // HS256 알고리즘을 사용하여 JWT 생성
        String jwt = Jwts.builder()
                .setSubject("user123")
                .signWith(key)
                .compact();

        System.out.println();
        System.out.println();
        System.out.println("Generated JWT: " + jwt);

        try {
            // 토큰 검증
            Jwts.parser().setSigningKey(key).parseClaimsJws(jwt);

            System.out.println("Token is valid. body " +Jwts.parser().setSigningKey(key).parseClaimsJws(jwt).getBody());
            System.out.println("Token is valid. sign " +Jwts.parser().setSigningKey(key).parseClaimsJws(jwt).getSignature());
            System.out.println("Token is valid. head " +Jwts.parser().setSigningKey(key).parseClaimsJws(jwt).getHeader());
        } catch (Exception e) {
            System.out.println("Token is not valid: " + e.getMessage());
        }

        System.out.println();
        System.out.println();

        // HS256 알고리즘을 사용하여 JWT 생성
        String testvalue1 = createToken("user123");

        System.out.println("testvalue1 JWT: " + jwt);

        try {
            // 토큰 검증
            Jwts.parser().setSigningKey(key).parseClaimsJws(testvalue1);
            System.out.println("testvalue1 is valid. body " +Jwts.parser().setSigningKey(key).parseClaimsJws(testvalue1).getBody());
            System.out.println("testvalue1 is valid. sign " +Jwts.parser().setSigningKey(key).parseClaimsJws(testvalue1).getSignature());
            System.out.println("testvalue1 is valid. head " +Jwts.parser().setSigningKey(key).parseClaimsJws(testvalue1).getHeader());
        } catch (Exception e) {
            System.out.println("testvalue1 Token is not valid: " + e.getMessage());
        }
        // HS256 알고리즘을 사용하여 JWT 생성
        Map<String, Object> claims = new HashMap<>();
        String testvalue3 = createToken(claims,"user123");


        System.out.println();
        System.out.println();
        System.out.println("testvalue3 JWT: " + jwt);

        System.out.println();
        System.out.println();
        try {
            // 토큰 검증
            Jwts.parser().setSigningKey(key).parseClaimsJws(testvalue3);
            System.out.println("testvalue3 is valid. body " +Jwts.parser().setSigningKey(key).parseClaimsJws(testvalue3).getBody());
            System.out.println("testvalue3 is valid. sign " +Jwts.parser().setSigningKey(key).parseClaimsJws(testvalue3).getSignature());
            System.out.println("testvalue3 is valid. head " +Jwts.parser().setSigningKey(key).parseClaimsJws(testvalue3).getHeader());
        } catch (Exception e) {
            System.out.println("testvalue3 Token is not valid: " + e.getMessage());
        }


        System.out.println();
        System.out.println();

        // HS256 알고리즘을 사용하여 JWT 생성

        String testvalue2 = createToken("1");

        System.out.println("testvalue2 JWT: " + jwt);

        try {
            // 토큰 검증
            boolean check = validateToken(testvalue2,"1");
            System.out.println("testvalue2 is valid. body " +Jwts.parser().setSigningKey(key).parseClaimsJws(testvalue2).getBody());
            System.out.println("testvalue2 is valid. sign " +Jwts.parser().setSigningKey(key).parseClaimsJws(testvalue2).getSignature());
            System.out.println("testvalue2 is valid. head " +Jwts.parser().setSigningKey(key).parseClaimsJws(testvalue2).getHeader());
        } catch (Exception e) {
            System.out.println("testvalue2 Token is not valid: " + e.getMessage());
        }
    }



    // 토큰에서 유저 id 추출
    public static String extractMemberId(String token) {
        System.out.println("extractMemberId + "+token);
        System.out.println("extractClaim(token, Claims::getSubject) + "+extractClaim(token, Claims::getSubject));
        return extractClaim(token, Claims::getSubject);
    }

    // 토큰 만료 여부 확인
    public static boolean isTokenExpired(String token) {
        System.out.println("isTokenExpired + "+ token);
        System.out.println("extractExpiration(token).before(new Date() + "+ extractExpiration(token).before(new Date()));
        return extractExpiration(token).before(new Date());
    }


    // 토큰 만료 날짜 추출
    public static Date extractExpiration(String token) {
        System.out.println("extractExpiration + "+ token);
        System.out.println("extractClaim(token, Claims::getExpiration) + "+ extractClaim(token, Claims::getExpiration).toString());

        return extractClaim(token, Claims::getExpiration);
    }

    // 토큰 유효성 검증
    public static boolean validateToken(String token, String num) {
        System.out.println("validateToken");
        final String memberId = extractMemberId(token);
        System.out.println("memberId : "+memberId);
        System.out.println("!isTokenExpired(token) : "+!isTokenExpired(token));
        System.out.println("memberId.equals(num) : "+memberId.equals(num));

        return (memberId.equals(num) && !isTokenExpired(token));
    }

    // 토큰에서 클레임 추출
    public static <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        System.out.println("extractClaim + "+token);
        final Claims claims = extractAllClaims(token);
        System.out.println("claims + "+claims.getSubject());
        System.out.println("claims + "+claims.getIssuer());
        System.out.println("claims + "+claims.getId());
        System.out.println("claims + "+claims.getExpiration());

        return claimsResolver.apply(claims);
    }

    // 토큰에서 모든 클레임 추출
    private static Claims extractAllClaims(String token) {
        System.out.println("extractAllClaims + "+token);


        System.out.println("keygetAlgorithm + "+key.getAlgorithm());
        System.out.println("key getFormat+ "+key.getFormat());
        System.out.println("keygetEncoded + "+key.getEncoded());
        System.out.println("key + "+key.toString());

        return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
    }


    // 토큰 생성
    private static String createToken(Map<String, Object> claims, String subject) {
        Date date = new Date();
        long time = System.currentTimeMillis() +  Long.parseLong(600000+"");
        date.setTime(time);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(date)
                .signWith(key)
                .compact();
    }

    // 토큰 생성
    private static String createToken(String subject) {
        Date date = new Date();
        long time = System.currentTimeMillis() +  Long.parseLong(600000+"");
        date.setTime(time);

        System.out.println("SignatureAlgorithm.HS256 : "+SignatureAlgorithm.HS256);

        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(date)
                .signWith(key)
                .compact();
    }
}
