package com.turtledoctor.kgu.chatbot.chathistory.repository;

import com.turtledoctor.kgu.entity.ChatHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatHistoryRepository extends JpaRepository<ChatHistory, Long> {

}
