package br.com.kydrem.whatsapp.messages;

import br.com.kydrem.whatsapp.chat.Chat;
import br.com.kydrem.whatsapp.chat.ChatRepository;
import br.com.kydrem.whatsapp.core.exceptions.BadRequestException;
import br.com.kydrem.whatsapp.core.websocket.WebSocketService;
import br.com.kydrem.whatsapp.user.User;
import br.com.kydrem.whatsapp.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ChatRepository chatRepository;
    @Autowired
    private WebSocketService webSocketService;

    public ResponseEntity<MessageDTO> sendMessage(MessageDTO messageDTO) {
        if (messageDTO.getChat() == null) {
            throw new BadRequestException("Não foi fornecido o chat");
        }
        if (messageDTO.getText().isEmpty()) {
            throw new BadRequestException("Mensagem em branco");
        }

        User loggedUser = userService.getLoggedUser();

        Optional<Chat> chatOptional = chatRepository.findById(messageDTO.getChat().getId());
        if (chatOptional.isEmpty()) {
            throw new BadRequestException("Não foi encontrado o chat");
        }

        Message message = new Message(messageDTO);
        message.setSender(loggedUser);
        message.setChat(chatOptional.get());
        message.setCreatedAt(LocalDateTime.now());

        Message savedMessage = messageRepository.save(message);

        Chat chat = chatOptional.get();
        chat.setLastMessageUpdate(LocalDateTime.now());
        chatRepository.save(chat);

        final List<MessageDTO> feedChat = findAllMessageFromChatAndUser(message.getChat().getId());

        webSocketService.notifyChatsUsers(message.getChat().getFromUser(), message.getChat().getToUser());
        webSocketService.notifyMessageUsers(message.getChat().getId(), feedChat);

        return ResponseEntity.ok(new MessageDTO(savedMessage));
    }

    public List<MessageDTO> findAllMessageFromChatAndUser(Long chatId) {
        User loggedUser = userService.getLoggedUser();

        List<Message> messageList = messageRepository.findAllMessageFromChatAndUser(chatId, loggedUser.getId());

        return messageList.stream().map((message) -> new MessageDTO(message)).collect(Collectors.toList());
    }
}
