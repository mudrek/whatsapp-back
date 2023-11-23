package br.com.kydrem.whatsapp.chat;

import br.com.kydrem.whatsapp.messages.MessageDTO;
import br.com.kydrem.whatsapp.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import org.springframework.context.annotation.Lazy;

@Getter
@Setter
@AllArgsConstructor
public class ChatDTO {
    private Long id;
    private UserDTO fromUser;
    private UserDTO toUser;
    @Lazy
    private List<MessageDTO> messageList;
    private MessageDTO lastMessage;

    public ChatDTO(Chat chat) {
        this.id = chat.getId();
        this.fromUser = UserDTO.basicInfo(chat.getFromUser());
        this.toUser = UserDTO.basicInfo(chat.getToUser());
        this.messageList = chat.getMessageList() != null ? chat.getMessageList().stream().map(MessageDTO::new).toList()
                : null;
    }
}
