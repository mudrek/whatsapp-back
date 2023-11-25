package br.com.kydrem.whatsapp.core.websocket;

import br.com.kydrem.whatsapp.chat.ChatDTO;
import br.com.kydrem.whatsapp.chat.ChatService;
import br.com.kydrem.whatsapp.messages.MessageDTO;
import br.com.kydrem.whatsapp.user.User;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketService {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private ChatService chatService;

    public void notifyChatsUsers(User fromUser, User toUser) {
        final List<ChatDTO> feedFromUser = chatService.findAllChatFromUser(fromUser);
        final List<ChatDTO> feedToUser = chatService.findAllChatFromUser(toUser);

        this.simpMessagingTemplate.convertAndSend("/chats." + fromUser.getId().toString(), feedFromUser);
        this.simpMessagingTemplate.convertAndSend("/chats." + toUser.getId().toString(), feedToUser);
    }

    public void notifyMessageUsers(Long chatId, List<MessageDTO> feedChat) {
        this.simpMessagingTemplate.convertAndSend("/chat." + chatId, feedChat);
    }
}
