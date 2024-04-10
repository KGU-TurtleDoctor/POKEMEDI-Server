package com.turtledoctor.kgu.chattext.repository;

import com.turtledoctor.kgu.entity.ChatText;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatTextRepository extends JpaRepository<ChatText, Long> {

}
