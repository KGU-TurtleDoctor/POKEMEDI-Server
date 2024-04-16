package com.turtledoctor.kgu.comment.Temp;

import com.turtledoctor.kgu.entity.Post;
import com.turtledoctor.kgu.response.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/Temp")
@RequiredArgsConstructor
public class TempPostController {
    private final TempPostRepository tempPostRepository;


    @GetMapping("/posts/{postId}")
    public ResponseEntity<ResponseDTO> returnPost(@PathVariable(name = "postId")Long postId){
        Post post = tempPostRepository.findById(postId).get();

        Map<String, Object> map = new HashMap<>();

        map.put("comments", post.getComments());

        ResponseDTO responseDTO = ResponseDTO.builder()
                .stateCode(200).isSuccess(true).result(map).build();

        return ResponseEntity.ok().body(responseDTO);
    }


}
