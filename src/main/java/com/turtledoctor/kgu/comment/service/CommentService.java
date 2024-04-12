package com.turtledoctor.kgu.comment.service;


import com.turtledoctor.kgu.comment.DTO.Request.CreateCommentRequest;
import com.turtledoctor.kgu.comment.DTO.Request.DeleteCommentRequest;
import com.turtledoctor.kgu.comment.DTO.Request.FindCommentsByPostRequest;
import com.turtledoctor.kgu.comment.DTO.Request.UpdateCommentRequest;
import com.turtledoctor.kgu.comment.DTO.Response.FindCommentsByPostResponse;
import com.turtledoctor.kgu.comment.DTO.Response.FindRepliesByCommentResponse;
import com.turtledoctor.kgu.comment.Temp.TempMemberRepository;
import com.turtledoctor.kgu.comment.Temp.TempPostRepository;
import com.turtledoctor.kgu.comment.repository.CommentRepository;
import com.turtledoctor.kgu.converter.DateConverter;
import com.turtledoctor.kgu.entity.Comment;
import com.turtledoctor.kgu.entity.Member;
import com.turtledoctor.kgu.entity.Post;
import com.turtledoctor.kgu.entity.Reply;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final TempPostRepository postRepository;
    private final TempMemberRepository memberRepository;

    @Transactional
    public Long createComment(CreateCommentRequest createCommentRequest, String authorization) throws Exception{
        //Authorization 변환 과정 추가 예정
        Long kakaoId = 1L;


        Member member = memberRepository.findByKakaoId(kakaoId);
        if(member == null){
            throw new Exception("없는 유저입니다.");
        }

        Optional<Post> optional = postRepository.findById(createCommentRequest.getPostId());

        if(optional.isEmpty()) throw new Exception("없는 게시글입니다.");
        Post post = optional.get();

        Comment comment = Comment.builder()
                .body(createCommentRequest.getBody())
                .post(post)
                .member(member)
                .build();

        Long id = commentRepository.save(comment).getId();

        return id;
    }

    @Transactional
    public Long updateComment(UpdateCommentRequest updateCommentRequest, String authorization, Long postId) throws Exception {
        //Authorization 변환 과정 추가 예정
        Long kakaoId = 1L;


        Optional<Comment> optional = commentRepository.findById(updateCommentRequest.getCommentId());
        if(optional.isEmpty()){
            throw new Exception("없는 comment입니다.");
        }

        Comment comment = optional.get();

        if(!comment.getMember().getKakaoId().equals(kakaoId)){
            //추후 자체 제작 Exception 으로변경
            log.info("request User : "+kakaoId);
            log.info("real User : "+ comment.getMember().getKakaoId());
            throw new Exception("사용자 권한이 없습니다.");
        }
        else if(!comment.getPost().getId().equals(postId)){
            throw new Exception("게시글에 없는 댓글입니다.");
        }
        else{
            comment.updateComment(updateCommentRequest.getBody());
        }

        commentRepository.save(comment);

        return comment.getId();
    }


    @Transactional(readOnly = true)
    public List<FindCommentsByPostResponse> findCommentByPost(FindCommentsByPostRequest commentsByPostRequest)throws Exception{
        List<FindCommentsByPostResponse> result = new ArrayList<>();

        Post post;
        Optional<Post> optional = postRepository.findById(commentsByPostRequest.getPostId());
        if(optional.isEmpty()){
            throw new Exception("없는 게시글입니다.");
        }else{
            post = optional.get();
        }

        List<Comment> comments = post.getCommentList();

        for(Comment comment : comments){
            LocalDateTime time;
            if(comment.getUpdatedAt()!=null){
                time = comment.getUpdatedAt();
            }else{
                time = comment.getCreatedAt();
            }
            result.add(
                FindCommentsByPostResponse.builder()
                        .commentId(comment.getId())
                        .nickName(comment.getMember().getNickname())
                        .body(comment.getBody())
                        .time(DateConverter.ConverteDate(time))
                        .replies(findRepliesByComment(comment.getReplyList()))
                        .build()
            );
        }

        return result;
    }


    private List<FindRepliesByCommentResponse> findRepliesByComment(List<Reply> replies){
        List<FindRepliesByCommentResponse> result = new ArrayList<>();

        for(Reply reply : replies){
            LocalDateTime time;
            if(reply.getUpdatedAt()!=null){
                time = reply.getUpdatedAt();
            }else{
                time = reply.getCreatedAt();
            }

            result.add(FindRepliesByCommentResponse.builder()
                    .replyId(reply.getId())
                    .time(DateConverter.ConverteDate(time))
                    .nickName(reply.getMember().getNickname())
                    .body(reply.getBody())
                    .build());
        }
        return result;
    }

    @Transactional
    public void deleteComment(DeleteCommentRequest deleteCommentRequest) throws Exception{
        Comment comment;
        Optional<Comment> optional = commentRepository.findById(deleteCommentRequest.getCommentId());

        if(optional.isEmpty()){
            throw new Exception("없는 댓글입니다.");
        }
        else{
            comment = optional.get();
        }

        commentRepository.delete(comment);
    }
}
