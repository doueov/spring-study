package com.example.mvc;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

class User {
    private String id;
    private String email;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User(String id, String email) {
        this.id = id;
        this.email = email;
    }
}

@RestController
@RequestMapping("/response_entity_demo")
public class ResponseEntityDemo {
    @GetMapping("/122")
    // 제네릭 타입이 Void인 이유는 해당 응답메시지에 바디데이터가 없기 때문임
    public ResponseEntity<Void> e122() {
        // 상태 코드와 관련된 메서드와 build 메서드 호출하여 바로 바디 데이터 없이 응답
        // return ResponseEntity.ok().build();
        // 바디데이터 없이 404 주고 싶은 경우
        // return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/1")
    // 제네릭 타입이 String이니 바디에 평문(text/plain)이 들어갈 것임을 유추 가능
    public ResponseEntity<String> e1() {
        String responseBody = "Hello, world!";
        // 상태 코드와 관련된 메소드에 바디 데이터를 같이 전달 가능
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/3")
    public ResponseEntity<String> e3() {
        // 좀 더 세분화해서 응답메시지를 커스터마이징하고 싶은 경우
        // 헤더 영역 정보 만들기
        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "value");
        String responseBody = "Hello, world!";
        return ResponseEntity
                .status(HttpStatus.OK) // 상태 코드
                .headers(headers) // 헤더
                .body(responseBody); // 바디
    }

    @GetMapping("/4")
    public ResponseEntity<String> e4() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "value");
        String responseBody = "Hello, world!";
        // 빌더 패턴 없이 직접 new 키워드로 객체 생성하여 응답
        return new ResponseEntity<>(
                responseBody, // 바디
                headers, // 헤더
                HttpStatus.OK); // 상태 코드
    }

    @GetMapping("/333")
    // 커스텀 클래스인 경우 내부적으로 JSON 직렬화 시도
    // (아마도 JSON 포맷을 압도적으로 많이 쓰므로 그냥 해당 메시지 컨버터를 쓰도록 하는 듯?, 아마 객체 직렬화의 기본 방식이 JSON인듯함)
    public ResponseEntity<User> e333() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new User("1", "hello@example.com"));
    }


}
