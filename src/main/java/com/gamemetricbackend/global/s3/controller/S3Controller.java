package com.gamemetricbackend.global.s3.controller;

import com.gamemetricbackend.global.s3.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * S3 파일 업로드를 처리하는 컨트롤러.
 * 클라이언트에서 파일을 업로드하면 S3에 저장하고 URL을 반환한다.
 */
@RestController
@RequestMapping("/s3")
@RequiredArgsConstructor
public class S3Controller {

    private final S3Service s3Service;

    /**
     * 이미지를 S3에 업로드하고, 업로드된 파일의 URL을 반환하는 API.
     *
     * @param file 업로드할 이미지 파일 (MultipartFile 형식)
     * @return 업로드된 이미지의 URL을 포함하는 JSON 응답
     */
    @PostMapping("/image")
    public ResponseEntity<Map<String, String>> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String fileUrl = s3Service.uploadFile(file);

            // JSON 형식의 응답 생성
            Map<String, String> response = new HashMap<>();
            response.put("url", fileUrl);

            return ResponseEntity.ok(response);
        } catch (IOException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "파일 업로드 실패: " + e.getMessage());

            return ResponseEntity.status(500).body(errorResponse);
        }
    }
}
