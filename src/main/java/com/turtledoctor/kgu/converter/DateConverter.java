package com.turtledoctor.kgu.converter;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class DateConverter {
    public static String ConverteDate(LocalDateTime localDateTime){
        log.info(localDateTime.toString());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
        ZonedDateTime zonedDateTime= localDateTime.atZone(ZoneId.of("UTC"));
        log.info(zonedDateTime.toLocalDateTime().toString());
        // LocalDateTime 객체를 문자열로 변환합니다.
        String formattedDateTime = zonedDateTime.withZoneSameInstant(ZoneId.of("Asia/Seoul")).toLocalDateTime().format(formatter);

        return formattedDateTime;
    }
}