package com.turtledoctor.kgu.upload.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ImageUploadDTO {
    String uploadFolder;
    int urlNumber;
}
