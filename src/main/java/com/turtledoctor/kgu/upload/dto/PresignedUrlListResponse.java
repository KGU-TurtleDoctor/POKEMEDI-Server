package com.turtledoctor.kgu.upload.dto;
import lombok.Builder;
import lombok.Getter;


@Builder
@Getter
public class PresignedUrlListResponse {
    String url;
}
