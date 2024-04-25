package com.example.mvc;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.stream.Collectors;

@Controller
public class MyController {
    // @RequestMapping(value="/hello", method = RequestMethod.GET)
    @GetMapping("/hello")
    public void hello(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setStatus(200);
        response.setHeader("Content-Type", "text/html; charset=utf-8");
        response.getWriter().write("<h1>hello</h1>");

        /*
        HTTP1.1 200 OK
        Content-Type: text/plain; charset=utf-8
        Content-Length: 5

        hello
        */
    }

    @GetMapping("/echo")
    public void echo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String method = request.getMethod();
        System.out.println("Method : " + method);

        String uri = request.getRequestURI();
        System.out.println("URI : " + uri);

        // localhost:3000/api/hello?asdf=1234&hello=world
        String query = request.getQueryString();
        System.out.println("Query String : " + query);

        // HTTP
        String protocol = request.getProtocol();
        System.out.println("Protocol : " + protocol);

        Enumeration<String> headerNames = request.getHeaderNames();
        while(headerNames != null && headerNames.hasMoreElements()) {
            String h = headerNames.nextElement();
            System.out.println(h + " : " + request.getHeader(h));
        }

        byte[] bytes = request.getInputStream().readAllBytes();
        String bytesToString = new String(bytes, StandardCharsets.UTF_8);
        System.out.println(bytesToString);

        response.setHeader("Content-Type", "text/plain; charset=utf-8");
        // 전달받은 body 텍스트를 그대로 응답하도록 설정
        response.getWriter().write(bytesToString);
    }

    @GetMapping("/hello-xml")
    public void helloXML(HttpServletResponse response) throws IOException {
        // 상태 코드와 관련된 상수를 제공하므로 이용해도 무방함
        response.setStatus(HttpStatus.OK.value());
        // "text/xml"이 아님을 유의
        response.setHeader("Content-Type", "application/xml; charset=utf-8");
        response.getWriter().write("<text>Hello</text>");
    }

    @GetMapping("/hello-json")
    public void helloJSON(HttpServletResponse response) throws IOException {
        response.setStatus(404);
        response.setHeader("Content-Type", "application/json");
        response.getWriter().write("{ \"data\": \"Hello\" }");
    }

    @GetMapping("/echo-repeat")
    public void echoRepeat(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setStatus(200);
        response.setHeader("Content-Type", "text/plain");
        // 커스텀 헤더인 X-Repeat-Count에 적힌 숫자 정보 가져오고 없으면 1로 초기화
        int loopCount = Integer.parseInt(request.getHeader("X-Repeat-Count") == null ? "1" : request.getHeader("X-Repeat-Count"));
        // 쿼리 스트링 정보 가져와서
        String query = request.getQueryString();
        // 쿼리 스트링 나누고
        String[] querySplit = query.split("&");
        String result = "";

        // 각 쿼리 스트링 정보들을 X-Repeat-Count만큼 반복해서 보여주기
        for(String s : querySplit) {
            for(int i=0;i<loopCount;i++) {
                String[] tmp = s.split("=");
                result += tmp[0] + "," + tmp[1] + "\n";
            }
        }

        response.getWriter().write(result.trim());
    }

    @GetMapping("/dog-image")
    public void dogImage(HttpServletResponse response) throws IOException {
        // resources 폴더의 static 폴더에 이미지 있어야 함
        File file = ResourceUtils.getFile("classpath:static/dog.jpg");
        // 파일의 바이트 데이터 모두 읽어오기
        byte[] bytes = Files.readAllBytes(file.toPath());

        response.setStatus(200);
        // 응답 메시지의 데이터가 JPEG 압축 이미지임을 설정
        response.setHeader("Content-Type", "image/jpeg");
        // 바이트 데이터 쓰기 (여기서는 텍스트 데이터를 전송하지 않기 떄문에 Writer 대신 OutputStream을 이용)
        response.getOutputStream().write(bytes);
    }

    @GetMapping("/dog-image-file")
    public void dogImageFile(HttpServletResponse response) throws IOException {
        File file = ResourceUtils.getFile("classpath:static/dog.jpg");
        byte[] bytes = Files.readAllBytes(file.toPath());

        response.setStatus(200);
        response.setHeader("Content-Type", "application/octet-stream");
        // response.setHeader("Content-Length", (bytes.length / 2) + "");
        String filename = "dog.jpg";
        response.setHeader("Content-Disposition", "attachment; filename=" + filename);
        response.getOutputStream().write(bytes);
    }

    private ArrayList<String> wordList = new ArrayList<>();

    @PostMapping("/words")
    public void addWord(HttpServletRequest request, HttpServletResponse response) throws IOException {
        /*
        POST 요청의
        바디에

        hello

        바디에

        hello
        world
        asdf
         */
        String bodyString = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        String[] words = bodyString.split("\n");
        for(String w : words) wordList.add(w.trim());

        response.setStatus(201);
        response.setHeader("Location", "/words");
    }

    @GetMapping("/words")
    public void showWords(HttpServletResponse response) throws IOException {
        String allWords = String.join(",", wordList);

        response.setStatus(200);
        response.getWriter().write(allWords);
    }

    @DeleteMapping("/words")
    public void deleteWords(HttpServletResponse response) {
        wordList.clear();
        response.setStatus(204);
        // response.setHeader("Location", "/words");
    }

    @GetMapping("/users/{username}/products/{productId}")
    public void getProducts(
            @PathVariable(value = "username", required = true) String username,
            @PathVariable("productId") Integer productId,
            @RequestParam(value = "show_comments", required = false, defaultValue = "true") Boolean showComments,
            @RequestHeader("API-Token") String apiToken,
            HttpServletResponse response) throws IOException {

        System.out.println(username);
        System.out.println(productId);
        System.out.println(showComments);
        System.out.println(apiToken);

        if(!apiToken.equals("secret")) {
            response.setStatus(401);
            response.getWriter().write("need valid api key");
        } else {
            response.setStatus(200);
            response.getWriter().write("success");
        }
    }



}















