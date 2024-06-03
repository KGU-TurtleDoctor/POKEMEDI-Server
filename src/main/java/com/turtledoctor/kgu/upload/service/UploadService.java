    package com.turtledoctor.kgu.upload.service;
    import com.turtledoctor.kgu.upload.dto.ImageUploadDTO;
    import com.turtledoctor.kgu.upload.dto.PresignedUrlListResponse;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.beans.factory.annotation.Autowired;
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

        @Value("${aws.s3.file.max-size}")
        private long maxFileSize;

        private final S3Presigner s3PreSigner;

        @Autowired
        public UploadService(S3Presigner s3PreSigner) {
            this.s3PreSigner = s3PreSigner;
        }
        public List<PresignedUrlListResponse> getPreSignedUrl(ImageUploadDTO imageUploadDTO) {

            List<PresignedUrlListResponse> urlList = new ArrayList<>();

            for(int i = 0; i < imageUploadDTO.getUrlNumber(); i++) {
                PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                        .bucket(bucket)
                        .key(imageUploadDTO.getUploadFolder() + "/" + UUID.randomUUID().toString())
                        .contentLength(maxFileSize) // 현재 용량 제한은 10MB. 환경변수에서 지정
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
    }
