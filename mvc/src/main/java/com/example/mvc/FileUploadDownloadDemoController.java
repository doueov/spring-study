package com.example.mvc;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class FileUploadDownloadDemoController {
    @PostMapping(value="/files/upload", consumes={ MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Object> uploadFile(
            @RequestParam Map<String, Object> nonFileParts,
            @RequestParam("file") MultipartFile file) throws IOException {
        for(String key : nonFileParts.keySet()) {
            System.out.println(key + " : " + nonFileParts.get(key).toString());
        }
        // 파일 이름 (+확장자)
        String originalFileName = file.getOriginalFilename();
        int dotIndex = originalFileName.lastIndexOf('.');
        // 파일 이름과 확장자 분리
        String filename = originalFileName.substring(0, dotIndex);
        String extension = originalFileName.substring(dotIndex + 1);
        System.out.println("filename : " + originalFileName + ", ext : " + extension);
        long unixTimeMillis = System.currentTimeMillis();
        // 새로운 파일 이름 생성
        String uploadedFileName = filename + "_" + unixTimeMillis + "." + extension;
        File uploadedFile = new File("uploaded/" + uploadedFileName);
        FileOutputStream fos = new FileOutputStream(uploadedFile);
        fos.write(file.getBytes());
        fos.close();

        return ResponseEntity.ok().build();
    }

    @GetMapping("/files/download")
    public ResponseEntity<byte[]> downloadFile(@RequestParam("src") String src) throws IOException {
        System.out.println(src);
        // 업로드 폴더의 파일 찾기
        File downloadedFile = new File("uploaded/" + src);

        if(!downloadedFile.isFile() || !downloadedFile.exists()) {
            // 폴더이거나 파일이 없으면 404 에러
            return ResponseEntity.notFound().build();
        } else {
            // 파일이고, 파일이 존재하는 경우에만 FIS 이용하여 바이트를 바디로 전달
            FileInputStream fis = new FileInputStream(downloadedFile);
            // 직접 헤더 설정하고
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/octet-stream");
            headers.add("Content-Disposition", "attachment; filename=" + downloadedFile.getName());
            // new로 ResponseEntity 객체를 만들어서 주되, 각각 (바디, 헤더, 응답코드) 전달
            ResponseEntity<byte[]> response = new ResponseEntity<>(fis.readAllBytes(), headers, HttpStatus.OK);
            fis.close();
            return response;
        }
    }



}








