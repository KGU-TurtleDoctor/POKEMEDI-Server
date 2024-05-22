package com.turtledoctor.kgu.upload.service;
import com.turtledoctor.kgu.upload.dto.ImageUploadDTO;
import com.turtledoctor.kgu.upload.dto.PresignedUrlListResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class UploadService {

    @Value("${aws.s3.bucket}")
    private String bucket;

    private final S3Presigner s3PreSigner;

    public UploadService(S3Presigner s3PreSigner) {
        this.s3PreSigner = s3PreSigner;
    }
    public List<PresignedUrlListResponse> getPreSignedUrl(ImageUploadDTO imageUploadDTO) {

//        String contentType = getContentType(imageUploadDTO.getExtension());
//        if (contentType == null) {
//            throw new IllegalArgumentException("Unsupported file type");
//        }

        List<PresignedUrlListResponse> urlList = new ArrayList<>();

        for(int i = 0; i < imageUploadDTO.getUrlNumber(); i++) {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(imageUploadDTO.getUploadFolder() + "/" + UUID.randomUUID().toString())
                    .contentType("image/" + imageUploadDTO.getExtension())
                    .build();

            PutObjectPresignRequest preSignRequest = PutObjectPresignRequest.builder()
                    .signatureDuration(Duration.ofMinutes(5)) // The URL will expire in 5 minutes.
                    .putObjectRequest(putObjectRequest)
                    .build();

            PresignedUrlListResponse dto = PresignedUrlListResponse.builder()
                    .url(s3PreSigner.presignPutObject(preSignRequest).url().toString())
                    .build();

            urlList.add(dto);
        }
        return urlList;
    }

//    private String getContentType(String path) {
//        Map<String, String> contentTypeMap = new HashMap<>();
//        contentTypeMap.put("webp", "image/webp");
//        contentTypeMap.put("jpg", "image/jpeg");
//        contentTypeMap.put("jpeg", "image/jpeg");
//        contentTypeMap.put("png", "image/png");
//        contentTypeMap.put("bmp", "image/bmp");
//        contentTypeMap.put("gif", "image/gif");
//
//        String extension = getFileExtension(path);
//        return contentTypeMap.get(extension.toLowerCase());
//    }
//
//    private String getFileExtension(String path) {
//        int lastIndexOfDot = path.lastIndexOf(".");
//        if (lastIndexOfDot == -1) {
//            return null;
//        }
//        return path.substring(lastIndexOfDot + 1);
//    }

}
