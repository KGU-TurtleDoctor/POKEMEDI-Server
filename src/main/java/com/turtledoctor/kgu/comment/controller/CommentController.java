package com.turtledoctor.kgu.comment.controller;

import com.turtledoctor.kgu.comment.DTO.Request.CreateCommentRequest;
import com.turtledoctor.kgu.comment.DTO.Request.DeleteCommentRequest;
import com.turtledoctor.kgu.comment.DTO.Request.FindCommentsByPostRequest;
import com.turtledoctor.kgu.comment.DTO.Request.UpdateCommentRequest;
import com.turtledoctor.kgu.comment.DTO.Response.CreateCommentResponse;
import com.turtledoctor.kgu.comment.ErrorMessage;
import com.turtledoctor.kgu.comment.service.CommentService;
import com.turtledoctor.kgu.response.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/api/community")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;


    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<ResponseDTO> findComments(@PathVariable(name="postId")Long postId){
        ResponseDTO responseDTO;
        FindCommentsByPostRequest findCommentsByPostRequest = new FindCommentsByPostRequest();
        findCommentsByPostRequest.setPostId(postId);
        Map<String, Object> result = new HashMap<>();


        try{
            responseDTO = ResponseDTO.builder()
                    .isSuccess(true)
                    .stateCode(200)
                    .result(commentService.findCommentByPost(findCommentsByPostRequest)).build();
            return ResponseEntity.ok().body(responseDTO);
        }catch(Exception e){
            result.put("error",e.getMessage());
            responseDTO = ResponseDTO.builder()
                    .isSuccess(false)
                    .stateCode(400)
                    .result((result))
                    .build();
            return ResponseEntity.badRequest().body(responseDTO);
        }

    }


    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<ResponseDTO> leaveComment(@PathVariable(name="postId") Long postId,/*@CookieValue(name = "Auth") String cookie,*/ @RequestBody CreateCommentRequest createCommentRequest){
        ResponseDTO responseDTO;

        createCommentRequest.setPostId(postId);

        try{                                                        //Login 기능 완성시 처리.
            Long id = commentService.createComment(createCommentRequest,"");
            CreateCommentResponse createCommentResponse = CreateCommentResponse.builder().commentId(id).build();
            responseDTO = ResponseDTO.builder()
                    .isSuccess(true).stateCode(200).result(createCommentResponse).build();
            return ResponseEntity.ok().body(responseDTO);
        }catch(Exception e){

            responseDTO = ResponseDTO.builder()
                    .isSuccess(false).stateCode(400).result(ErrorMessage.builder().errorMessage(e.getMessage()).build()).build();
            return ResponseEntity.badRequest().body(responseDTO);
        }

    }


    @PutMapping("/posts/{postid}/comments/{commentid}")
    public ResponseEntity<ResponseDTO> updateComments(@PathVariable(name="postid") Long postId, @PathVariable(name="commentid") Long commentId, @RequestBody UpdateCommentRequest updateCommentRequest){
        updateCommentRequest.setCommentId(commentId);
        updateCommentRequest.setPostId(postId);
        ResponseDTO responseDTO;
        Map<String, Object> result = new HashMap<>();

        try{
            result.put("id",commentService.updateComment(updateCommentRequest, "", postId));
            responseDTO = ResponseDTO.builder()
                    .isSuccess(true)
                    .stateCode(200)
                    .result(result).build();

            return ResponseEntity.ok().body(responseDTO);

        }catch (Exception e){
            result.put("error",e.getMessage());
            responseDTO = ResponseDTO.builder()
                    .isSuccess(false)
                    .stateCode(400)
                    .result(result)
                    .build();

            return ResponseEntity.badRequest().body(responseDTO);
        }

    }

    @DeleteMapping("/posts/{postid}/comments/{commentid}")
    public ResponseEntity<ResponseDTO> deleteComments(@PathVariable(name="postid") Long postId, @PathVariable(name="commentid") Long commentId){
        DeleteCommentRequest deleteCommentRequest = DeleteCommentRequest.builder()
                .commentId(commentId)
                .postId(postId)
                .build();

        try{
            commentService.deleteComment(deleteCommentRequest);
            return ResponseEntity.ok().body(ResponseDTO.builder().isSuccess(true).stateCode(200).build());
        }catch(Exception e){
            return ResponseEntity
                    .badRequest()
                    .body(ResponseDTO.builder().isSuccess(false).stateCode(400)
                            .result(ErrorMessage.builder().errorMessage(e.getMessage()).build())
                            .build());

        }


    }

}
