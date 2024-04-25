package com.example.mvc;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
import java.util.HashMap;
import java.util.stream.Collectors;

class MyCommandObject {
    private String value1;
    private Integer value2;

    public void setValue1(String value1) {
        this.value1 = value1;
    }

    public void setValue2(Integer value2) {
        this.value2 = value2;
    }

    @Override
    public String toString() {
        return "MyCommandObject{value1='" + value1 + '\'' + ", value2=" + value2 + '}';
    }
}

class SignUpFormData {
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
}

class MyJsonData {
    private String value1;
    private Integer value2;

    public String getValue1() { return value1; }
    public Integer getValue2() { return value2; }
    public void setValue1(String value1) { this.value1 = value1; }
    public void setValue2(Integer value2) { this.value2 = value2; }

    @Override
    public String toString() {
        return "MyJsonData{value1='" + value1 + '\'' + ", value2=" + value2 + '}';
    }
}


@RestController
@RequestMapping("/renew")
public class MyRenewController {
    @GetMapping(value = "/json-test",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public MyJsonData jsonTest(@RequestBody MyJsonData myJsonData) {
        System.out.println(myJsonData);
        return myJsonData;
    }

    @PostMapping(value = "/sign_up",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String signUp(@RequestBody SignUpFormData signUpFormData) {
        return "success";
    }


    @PostMapping(value = "/register_class", consumes=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public HashMap<String, Object> postRegisterForm(@RequestBody HashMap<String, Object> registerClassForm) {
        System.out.println(registerClassForm);

        HashMap<String, Object> responseJSON = new HashMap<>();
        responseJSON.put("result", "success");
        responseJSON.put("isSuccess", true);
        responseJSON.put("credit", 3);
        System.out.println(responseJSON);

        return responseJSON;
    }

//    @PostMapping("/sign_up")
//    public String signUp(SignUpFormData signUpFormData) {
//        return signUpFormData.toString();
//    }


    @PostMapping("/test")
    public String commandObjectTest(@ModelAttribute MyCommandObject myCommandObject) {
        return myCommandObject.toString();
    }


    @GetMapping(value = "/echo", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String echo(@RequestBody String content) {
        return content;
    }
    
    @GetMapping(value = "/hello-html", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String helloHTML() {
        return "<h1>Hello</h1>";
    }

    @GetMapping(value = "/echo-repeat", produces = MediaType.TEXT_PLAIN_VALUE)
    public String echoRepeat(
            @RequestParam("word") String word,
            @RequestHeader(value = "X-Repeat-Count", defaultValue = "1") Integer repeatCount) throws IOException {
        String result = "";
        for(int i=0;i<repeatCount;i++) {
            result += word;
        }
        return result;
    }

    @GetMapping(value = "/dog-image")
    public byte[] dogImage() throws IOException {
        File file = ResourceUtils.getFile("classpath:static/dog.jpg");
        return Files.readAllBytes(file.toPath());
    }

    @GetMapping(value = "/dog-image-file", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> dogImageFile() throws IOException {
        File file = ResourceUtils.getFile("classpath:static/dog.jpg");
        byte[] bytes = Files.readAllBytes(file.toPath());

        String filename = "dog.jpg";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=" + filename);

        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }

}
