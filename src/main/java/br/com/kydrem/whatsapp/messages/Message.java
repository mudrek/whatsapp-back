package br.com.kydrem.whatsapp.messages;

import br.com.kydrem.whatsapp.chat.Chat;
import br.com.kydrem.whatsapp.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Lazy;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @Lazy
    private Chat chat;
    private String text;
    @ManyToOne
    private User sender;

    public Message(MessageDTO messageDTO) {
        this.id = messageDTO.getId();
        this.chat = new Chat(messageDTO.getChat());
        this.text = messageDTO.getText();
        this.sender = new User(messageDTO.getSender());
    }
}
