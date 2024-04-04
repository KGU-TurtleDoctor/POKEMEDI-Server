package com.turtledoctor.kgu.testPackage.controller;


import com.turtledoctor.kgu.response.ResponseDTO;
import com.turtledoctor.kgu.testPackage.service.TempMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/temp")
@RequiredArgsConstructor
public class TempDataController {

    private final TempMemberService tempMemberService;

    @GetMapping("/makeMember")
    public ResponseEntity<ResponseDTO> makeTempMember(@RequestParam(name = "kakaoId") Long kakaoId){
        tempMemberService.createTempMember(kakaoId);

        ResponseDTO responseDTO =  ResponseDTO.builder()
                .stateCode(200)
                .isSuccess(true)
                .result(kakaoId)
                .build();

        return ResponseEntity.ok().body(responseDTO);
    }
}
