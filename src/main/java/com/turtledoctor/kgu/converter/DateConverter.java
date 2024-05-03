package com.turtledoctor.kgu.converter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateConverter {
    public static String ConverteDate(LocalDateTime localDateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
        localDateTime.atZone(ZoneId.of("Asia/Seoul"));
        // LocalDateTime 객체를 문자열로 변환합니다.
        String formattedDateTime = localDateTime.format(formatter);

        return formattedDateTime;
    }
}