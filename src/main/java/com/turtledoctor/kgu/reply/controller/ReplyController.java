package com.turtledoctor.kgu.reply.controller;

import com.turtledoctor.kgu.auth.jwt.JWTUtil;
import com.turtledoctor.kgu.entity.Reply;
import com.turtledoctor.kgu.reply.ErrorMessage;
import com.turtledoctor.kgu.reply.dto.CreateReplyDTO;
import com.turtledoctor.kgu.reply.dto.DeleteReplyDTO;
import com.turtledoctor.kgu.reply.dto.UpdateReplyDTO;
import com.turtledoctor.kgu.reply.dto.request.CreateReplyRequest;
import com.turtledoctor.kgu.reply.dto.request.UpdateReplyRequest;
import com.turtledoctor.kgu.reply.dto.response.CreateReplyResponse;
import com.turtledoctor.kgu.reply.service.ReplyService;
import com.turtledoctor.kgu.response.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/api/community")
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;


    @PostMapping("/posts/{postId}/comments/{commentId}/replies")
    public ResponseEntity<ResponseDTO> leaveReply(@CookieValue(name = "Authorization") String author,@PathVariable(name = "postId") Long postId, @PathVariable(name = "commentId") Long commentId, @RequestBody CreateReplyRequest createReplyRequest){

        CreateReplyDTO createReplyDTO = CreateReplyDTO.builder()
                .authorization(author)
                .postId(postId)
                .commentId(commentId)
                .body(createReplyRequest.getBody())
                .build();
        try{
            Long id = replyService.createReply(createReplyDTO);

            ResponseDTO responseDTO = ResponseDTO.builder()
                    .isSuccess(true).stateCode(200).build();

            return ResponseEntity.ok().body(responseDTO);
        }catch (Exception e){
            ErrorMessage errorMessage = ErrorMessage.builder()
                    .error(e.getMessage()).build();

            ResponseDTO responseDTO = ResponseDTO.builder()
                    .isSuccess(false).stateCode(400).result(errorMessage).build();

            return ResponseEntity.badRequest().body(responseDTO);
        }

    }

    @PutMapping("/posts/{postId}/comments/{commentId}/replies/{replyId}")
    public ResponseEntity<ResponseDTO> updateReply(@CookieValue(name = "Authorization") String author,@PathVariable(name = "postId") Long postId, @PathVariable(name = "commentId") Long commentId,@PathVariable(name = "replyId") Long replyId, @RequestBody UpdateReplyRequest updateReplyRequest){
        UpdateReplyDTO updateReplyDTO = UpdateReplyDTO.builder()
                .authorization(author)
                .postId(postId)
                .commentId(commentId)
                .replyId(replyId)
                .body(updateReplyRequest.getBody())
                .build();

        try{
            Long id = replyService.updateReply(updateReplyDTO);

            ResponseDTO responseDTO = ResponseDTO.builder()
                    .isSuccess(true)
                    .stateCode(200)
                    .build();

            return ResponseEntity.ok().body(responseDTO);
        }catch (Exception e){
            ErrorMessage errorMessage = ErrorMessage.builder()
                    .error(e.getMessage()).build();

            ResponseDTO responseDTO = ResponseDTO.builder()
                    .isSuccess(false).stateCode(400).result(errorMessage).build();

            return ResponseEntity.badRequest().body(responseDTO);
        }

    }

    @DeleteMapping("/posts/{postId}/comments/{commentId}/replies/{replyId}")
    public ResponseEntity<ResponseDTO> deleteReply(@CookieValue(name = "Authorization") String author,@PathVariable(name = "postId") Long postId, @PathVariable(name = "commentId") Long commentId,@PathVariable(name = "replyId") Long replyId, @RequestBody UpdateReplyRequest updateReplyRequest){
        DeleteReplyDTO deleteReplyDTO = DeleteReplyDTO.builder()
                .authorization(author)
                .postId(postId)
                .commentId(commentId)
                .replyId(replyId).build();

        try{
            replyService.deleteReply(deleteReplyDTO);

            return ResponseEntity.ok().build();
        }catch (Exception e){
            ErrorMessage errorMessage = ErrorMessage.builder()
                    .error(e.getMessage()).build();

            ResponseDTO responseDTO = ResponseDTO.builder()
                    .isSuccess(false).stateCode(400).result(errorMessage).build();

            return ResponseEntity.badRequest().body(responseDTO);
        }
    }
}
