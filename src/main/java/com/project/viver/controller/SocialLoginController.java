package com.project.viver.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.project.viver.api.KakaoTokenClient;
import com.project.viver.dto.social.KakaoToken;
import com.project.viver.dto.token.KakaoTokenDto;
import com.project.viver.service.login.KakaoService;
import com.project.viver.service.login.LoginService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class SocialLoginController {

    private final KakaoTokenClient kakaoTokenClient;
    
    @Autowired
    KakaoService kakaoService;
    
    @Autowired
    LoginService loginService;

    @Value("${kakao.client.id}")
    private String clientId;

    @Value("${kakao.client.secret}")
    private String clientSecret;

    @GetMapping("/oauth/kakao/callback")
    public Map<String,Object> kakaoLogin(HttpServletRequest request) throws Exception {
        String code = request.getParameter("code");
        KakaoToken KakaoToken = kakaoService.getKakaoAccessToken(code);
        return loginService.kakaoLogin(KakaoToken);
    }

//    @GetMapping("/oauth/kakao/callback")
//    public void kakaoLoginCallback(String code, HttpServletResponse response) throws IOException {
//        String contentType = "application/x-www-form-urlencoded;charset=utf-8";
//        KakaoTokenDto.Request kakaoTokenRequestDto = KakaoTokenDto.Request.builder()
//                .client_id(clientId)
//                .client_secret(clientSecret)
//                .grant_type("authorization_code")
//                .code(code)
//                .redirect_uri("http://localhost:8080/oauth/kakao/callback")
//                .build();
//        KakaoTokenDto.Response kakaoToken = kakaoTokenClient.requestKakaoToken(contentType, kakaoTokenRequestDto);
//        response.sendRedirect("http://localhost:3000/sociallogin?token="+kakaoToken.getAccess_token());
//    }
    
    @GetMapping("/oauth/naver/callback")
    public void naverLoginCallback(String code, HttpServletResponse response) throws IOException {
        String contentType = "application/x-www-form-urlencoded;charset=utf-8";
        KakaoTokenDto.Request kakaoTokenRequestDto = KakaoTokenDto.Request.builder()
                .client_id(clientId)
                .client_secret(clientSecret)
                .grant_type("authorization_code")
                .code(code)
                .redirect_uri("http://localhost:8080/oauth/naver/callback")
                .build();
        KakaoTokenDto.Response kakaoToken = kakaoTokenClient.requestKakaoToken(contentType, kakaoTokenRequestDto);
        response.sendRedirect("http://localhost:3000/sociallogin?token="+kakaoToken.getAccess_token());
    }

}