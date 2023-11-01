package br.com.kydrem.whatsapp.chat;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    Optional<Chat> findByFromUserIdAndToUserId(Long fromUserId, Long toUserId);
}
