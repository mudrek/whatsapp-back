package br.com.kydrem.whatsapp.chat;

import br.com.kydrem.whatsapp.messages.Message;
import br.com.kydrem.whatsapp.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private User fromUser;
    @ManyToOne
    private User toUser;
    @OneToMany
    private List<Message> messageList;

    public Chat(ChatDTO chatDTO) {
        this.id = chatDTO.getId();
        this.fromUser = chatDTO.getFromUser() != null ? new User(chatDTO.getFromUser()) : null;
        this.toUser =chatDTO.getToUser() != null ? new User(chatDTO.getToUser()) : null;
        this.messageList = chatDTO.getMessageList() != null ? chatDTO.getMessageList().stream().map(Message::new).toList() : null;
    }
}
