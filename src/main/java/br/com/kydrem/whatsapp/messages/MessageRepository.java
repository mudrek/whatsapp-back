package br.com.kydrem.whatsapp.messages;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("SELECT message FROM Message message INNER JOIN message.chat chat WHERE chat.id = :chatId ORDER BY message.createdAt DESC LIMIT 1")
    Optional<Message> lastMessageSendFromChat(@Param("chatId") Long chatId);

    @Query("SELECT message FROM Message message " +
            "INNER JOIN message.chat chat " +
            "INNER JOIN chat.fromUser fromUser " +
            "INNER JOIN chat.toUser toUser " +
            "WHERE chat.id = :chatId AND (fromUser.id = :userId OR toUser.id = :userId) " +
            "ORDER BY message.createdAt ASC")
    List<Message> findAllMessageFromChatAndUser(@Param("chatId") Long chatId, @Param("userId") Long userId);
}
