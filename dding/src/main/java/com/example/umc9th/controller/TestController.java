package com.example.umc9th.controller; // 방금 만든 패키지 이름

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/") // http://localhost:8080/ 주소로 접속 시
    public String hello() {
        // 이 메시지가 보이면 모든 설정이 끝난 것입니다.
        return "축하합니다! DB 연결 및 서버 실행 성공!";
    }
}