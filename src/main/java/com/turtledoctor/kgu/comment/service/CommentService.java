package com.turtledoctor.kgu.comment.service;


import com.turtledoctor.kgu.comment.DTO.Request.CreateCommentRequest;
import com.turtledoctor.kgu.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public Object createComment(CreateCommentRequest createCommentRequest){


    }
}
