package br.com.kydrem.whatsapp.messages;

import java.time.LocalDateTime;

import br.com.kydrem.whatsapp.chat.ChatDTO;
import br.com.kydrem.whatsapp.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {
    private Long id;
    private ChatDTO chat;
    private String text;
    private UserDTO sender;
    private LocalDateTime createdAt;

    public MessageDTO(Message message) {
        this.id = message.getId();
        // this.chat = new ChatDTO(message.getChat());
        this.text = message.getText();
        this.sender = new UserDTO(message.getSender());
        this.createdAt = message.getCreatedAt();
    }

}
