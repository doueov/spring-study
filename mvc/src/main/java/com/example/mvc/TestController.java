package com.example.mvc;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
@RequestBody로 전달받을 클래스 정의

{
    "message": "world",
    "from": "@mirim_1",
    "to": "yubs",
    "importance": 5
}
*/

class RequestData {
    private String message;
    private String from;
    private String to;
    private int importance;

    // 게터, 세터
    public String getMessage() {return message;}public void setMessage(String message) {this.message = message;}public String getFrom() {return from;}public void setFrom(String from) {this.from = from;}public String getTo() {return to;}public void setTo(String to) {this.to = to;}public int getImportance() {return importance;}public void setImportance(int importance) {this.importance = importance;}
}

/*
@ResponseBody로 전달할 클래스 정의

{
    "result": "success",
    "code": 1001
}
*/

class ResponseData {
    private String result;
    private int code;

    // 게터, 세터
    public String getResult() {return result;}public void setResult(String result) {this.result = result;}public int getCode() {return code;}public void setCode(int code) {this.code = code;}
}

/*
    "title": "Harry Porter",
    "author": "J. K. Rowling",
    "first_realese": 1997,
    "rating": 4.5
*/
class BookInfo {
    private String title;
    private String author;
    private int first_release;
    private double rating;

    public String getTitle() {return title;}public void setTitle(String title) {this.title = title;}public String getAuthor() {return author;}public void setAuthor(String author) {this.author = author;}public int getFirst_release() {return first_release;}public void setFirst_release(int first_release) {this.first_release = first_release;}public double getRating() {return rating;}public void setRating(double rating) {this.rating = rating;}
}

class BookInsertSuccessMessage {
    private String result;
    private String id;
    private boolean success;

    public String getResult() {return result;}public void setResult(String result) {this.result = result;}public String getId() {return id;}public void setId(String id) {this.id = id;}public boolean isSuccess() {return success;}public void setSuccess(boolean success) {this.success = success;}
}

class RepeatStringMessage {
    private String text;
    private int count;

    public String getText() {return text;}public void setText(String text) {this.text = text;}public int getCount() {return count;}public void setCount(int count) {this.count = count;}
}

// 내부 객체용 클래스 정의
class InnerObject {
    private String innerValue1;

    // 세터, 게터 추가
    public String getInnerValue1() {return innerValue1;}
    public void setInnerValue1(String innerValue1) {this.innerValue1 = innerValue1;}
}

class JsonDataWithArrayAndInnerObject {
    private List<String> array1; // String 형식의 데이터만 존재
    private List<Object> array2; // 다양한 데이터 형식이 혼재
    private InnerObject inner; // 내부 객체

    // 세터, 게터 추가
    public List<String> getArray1() {return array1;}
    public void setArray1(List<String> array1) {this.array1 = array1;}
    public List<Object> getArray2() {return array2;}
    public void setArray2(List<Object> array2) {this.array2 = array2;}
    public InnerObject getInner() {return inner;}
    public void setInner(InnerObject inner) {this.inner = inner;}
}

@RestController
@RequestMapping("/test")
public class TestController {
    /*
    @GetMapping(value = "/json-test-2",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public JsonDataWithArrayAndInnerObject jsonTest2(@RequestBody JsonDataWithArrayAndInnerObject jsonDataWithArrayAndInnerObject) {
        for(Object item : jsonDataWithArrayAndInnerObject.getArray2()) {
            if(item != null) System.out.println(item.getClass().getName());
        }
        return jsonDataWithArrayAndInnerObject;
    }
    */

    @GetMapping(value = "/json-test-2",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String jsonTest2(@RequestBody HashMap<String, Object> jsonDataWithArrayAndInnerObject) {
        List<String> array1 = (List<String>) jsonDataWithArrayAndInnerObject.get("array1");
        List<Object> array2 = (List<Object>) jsonDataWithArrayAndInnerObject.get("array2");
        Map<String, Object> inner = (Map<String, Object>) jsonDataWithArrayAndInnerObject.get("inner");
        System.out.println(array1);
        System.out.println(array2);
        System.out.println(inner.get("innerValue1"));
        return "Hello";
    }

    @GetMapping("/test1")
    @ResponseBody
    public ResponseData test1(@RequestBody RequestData requestData) {
        System.out.println(requestData.getMessage());
        ResponseData responseData = new ResponseData();
        responseData.setResult("success");
        responseData.setCode(1001);
        return responseData;
    }

    @PostMapping("/test2")
    @ResponseBody
    public BookInsertSuccessMessage test2(@RequestBody BookInfo bookInfo) {
        System.out.println(bookInfo.getTitle());
        BookInsertSuccessMessage responseData = new BookInsertSuccessMessage();
        responseData.setResult("created");
        responseData.setId("a1234");
        responseData.setSuccess(true);
        return responseData;
    }

    /*
    @GetMapping("/test3")
    @ResponseBody
    public String test(@RequestBody RepeatStringMessage message) {
        int count = message.getCount();
        String result = "";
        for(int i=0;i<count;i++) result += message.getText();
        return result;
    }
    */

    @GetMapping("/test3")
    @ResponseBody
    public String test(@RequestBody HashMap<String, Object> message) {
        int count = (Integer) message.get("count");
        String result = "";
        for(int i=0;i<count;i++) result += (String) message.get("text");
        return result;
    }

    @PostMapping(value = "/register_class", consumes=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public HashMap<String, Object> postRegisterForm(
            @RequestBody HashMap<String, Object> registerClassForm) {
        System.out.println(registerClassForm);

        // 응답을 위한 JSON 값 생성을 위해서 역시 HashMap 이용
        HashMap<String, Object> responseJSON = new HashMap<>();
        responseJSON.put("result", "success");
        responseJSON.put("isSuccess", true);
        responseJSON.put("credit", 3);
        System.out.println(responseJSON);

        return responseJSON;
    }
}

















