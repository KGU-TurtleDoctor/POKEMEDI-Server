package com.turtledoctor.kgu.chathistory.repository;

import com.turtledoctor.kgu.entity.ChatHistory;
import com.turtledoctor.kgu.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatHistoryRepository extends JpaRepository<ChatHistory, Long> {
    public ChatHistory findTop1ByMemberOrderByCreatedAtDesc(Member member);
    public List<ChatHistory> findChatHistoryByMemberOrderByUpdatedAtDesc(Member member);
}
