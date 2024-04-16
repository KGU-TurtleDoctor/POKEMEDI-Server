package com.turtledoctor.kgu.reply.service;

import com.turtledoctor.kgu.entity.Comment;
import com.turtledoctor.kgu.entity.Member;
import com.turtledoctor.kgu.entity.Post;
import com.turtledoctor.kgu.entity.Reply;
import com.turtledoctor.kgu.reply.Temp.TempCommentRepository;
import com.turtledoctor.kgu.reply.Temp.TempMemberRepsitory;
import com.turtledoctor.kgu.reply.Temp.TempPostRepository;
import com.turtledoctor.kgu.reply.dto.CreateReplyDTO;
import com.turtledoctor.kgu.reply.dto.DeleteReplyDTO;
import com.turtledoctor.kgu.reply.dto.UpdateReplyDTO;
import com.turtledoctor.kgu.reply.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final TempMemberRepsitory memberRepository;
    private final TempCommentRepository commentRepository;
    private final TempPostRepository postRepository;

    @Transactional
    public Long createReply(CreateReplyDTO createReplyDTO) throws Exception{

        //이후 kakao Id 획득 로직으로 변환
        Long kakaoId = 1L;
        Post post;
        Comment comment;
        Member member = memberRepository.findMemberByKakaoId(kakaoId);
        if(member == null) throw new Exception("없는 회원입니다.");
        {
            Optional<Comment> optionalComment = commentRepository.findById(createReplyDTO.getCommentId());
            if (optionalComment.isEmpty()) {
                throw new Exception("요청하신 댓글은 없는 댓글입니다.");
            } else {
                comment = optionalComment.get();
            }
        }
        {
            Optional<Post> optionalPost = postRepository.findById(createReplyDTO.getPostId());
            if(optionalPost.isEmpty()){
                throw new Exception("요청하신 게시글은 없는 게시글입니다.");
            }
            else{
                post = optionalPost.get();
            }
        }
        Reply reply = Reply.builder()
                .comment(comment)
                .body(createReplyDTO.getBody())
                .member(member)
                .build();

        Reply result = replyRepository.save(reply);
        post.plusComments();
        postRepository.save(post);
        return result.getId();
    }

    @Transactional
    public Long updateReply(UpdateReplyDTO updateReplyDTO) throws Exception{
        //이후 kakao Id 획득 로직으로 변환
        Long kakaoId = 1L;
        Reply reply;
        Member member = memberRepository.findMemberByKakaoId(kakaoId);

        if(member == null) throw new Exception("없는 회원입니다.");

        Optional<Comment> optionalC = commentRepository.findById(updateReplyDTO.getCommentId());
        if(optionalC.isEmpty()) throw new Exception("요청하신 댓글은 없는 댓글입니다.");
        Comment comment = optionalC.get();
        {
            Optional<Reply> optionalReply = replyRepository.findById(updateReplyDTO.getReplyId());
            if(optionalReply.isEmpty()) throw new Exception("요청하신 답글은 없는 답글입니다.");

            reply = optionalReply.get();
            if(!reply.getMember().getKakaoId().equals(kakaoId)) throw new Exception("권한이 없습니다.");
        }

        reply.updateReply(updateReplyDTO.getBody());

        return replyRepository.save(reply).getId();
    }

    @Transactional
    public void deleteReply(DeleteReplyDTO deleteReplyDTO) throws Exception{
        //이후 kakao Id 획득 로직으로 변환
        Long kakaoId = 1L;

        Post post;
        Reply reply;
        {
            Optional<Post> optionalPost = postRepository.findById(deleteReplyDTO.getPostId());
            if(optionalPost.isEmpty()){
                throw new Exception("요청하신 게시글은 없는 게시글입니다.");
            }
            else{
                post = optionalPost.get();
            }
        }

        {
            Optional<Reply> optionalReply = replyRepository.findById(deleteReplyDTO.getReplyId());
            if(optionalReply.isEmpty()) throw new Exception("요청하신 답글은 없는 답글입니다.");

            reply = optionalReply.get();
            if(!reply.getMember().getKakaoId().equals(kakaoId)) throw new Exception("권한이 없습니다.");
        }

        replyRepository.delete(reply);
    }
}
