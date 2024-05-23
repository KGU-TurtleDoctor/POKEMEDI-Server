package com.turtledoctor.kgu.upload.controller;

import com.turtledoctor.kgu.response.ResponseDTO;
import com.turtledoctor.kgu.upload.dto.ImageUploadDTO;
import com.turtledoctor.kgu.upload.service.UploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/upload")
@RequiredArgsConstructor
@Slf4j
public class UploadController {

    private final UploadService uploadService;

    @GetMapping ("")
    public ResponseEntity<ResponseDTO> getPreSignedUrlList(
            @RequestParam("using") String uploadFolder,
            @RequestParam("number") int urlNumber
    ) {
        ImageUploadDTO imageUploadDTO = ImageUploadDTO.builder()
                .uploadFolder(uploadFolder)
                .urlNumber(urlNumber)
                .build();

        ResponseEntity<ResponseDTO> response = ResponseEntity.ok().body(ResponseDTO.builder()
                .isSuccess(true)
                .stateCode(200)
                .result(uploadService.getPreSignedUrl(imageUploadDTO))
                .build());

        return response;
    }
}
