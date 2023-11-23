package br.com.kydrem.whatsapp.chat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    Optional<Chat> findByFromUserIdAndToUserId(Long fromUserId, Long toUserId);

    @Query("SELECT chat FROM Chat chat " +
            "INNER JOIN chat.fromUser fromUser " +
            "INNER JOIN chat.toUser toUser " +
            "WHERE fromUser.id = :userId OR toUser.id = :userId")
    List<Chat> findAllChatByUserId(@Param("userId") Long userId);
}
