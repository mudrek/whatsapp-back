package br.com.kydrem.whatsapp.chat;

import br.com.kydrem.whatsapp.messages.MessageDTO;
import br.com.kydrem.whatsapp.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ChatDTO {
    private Long id;
    private UserDTO fromUser;
    private UserDTO toUser;
    private List<MessageDTO> messageList;

    public ChatDTO(Chat chat) {
        this.id = chat.getId();
        this.fromUser = new UserDTO(chat.getFromUser());
        this.toUser = new UserDTO(chat.getToUser());
        this.messageList = chat.getMessageList() != null ? chat.getMessageList().stream().map(MessageDTO::new).toList() : null;
    }
}
