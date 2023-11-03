package br.com.kydrem.whatsapp.core.websocket;

import br.com.kydrem.whatsapp.messages.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketService {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    public void sendNewMessage(Message message, String chatId) {
        this.simpMessagingTemplate.convertAndSend("/chat." + chatId, message);
    }
}
