package com.example.zerobase.zerobaseminiassignment.test;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;


@Configuration
public class TestServletConfig {

    @Value("${link.url}")
    String url;

    @Bean
    public ServletRegistrationBean<TestServletCall> customServlet() {
        System.out.println("url : "+url);

        if(StringUtils.hasText(url)){
            System.out.println("hasText true");
        }else {

            System.out.println("hasText false");
        }
        if(url.startsWith("http")){
            System.out.println("startsWith true");
        }else {

            System.out.println("startsWith false");
        }


        if(StringUtils.hasText(url)&&url.startsWith("http")){
            url = "/crypt";
        }else{
            url = "/local/crypt";
        }

        return new ServletRegistrationBean<>( new TestServletCall(),url );
    }


}
