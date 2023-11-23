package br.com.kydrem.whatsapp.messages;

import br.com.kydrem.whatsapp.chat.Chat;
import br.com.kydrem.whatsapp.chat.ChatRepository;
import br.com.kydrem.whatsapp.core.exceptions.BadRequestException;
import br.com.kydrem.whatsapp.core.websocket.WebSocketService;
import br.com.kydrem.whatsapp.user.User;
import br.com.kydrem.whatsapp.user.UserRepository;
import br.com.kydrem.whatsapp.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ChatRepository chatRepository;
    @Autowired
    private WebSocketService webSocketService;

    public ResponseEntity<MessageDTO> sendMessage(MessageDTO messageDTO) {
        if(messageDTO.getChat() == null) {
            throw new BadRequestException("Não foi fornecido o chat");
        }
        if(messageDTO.getSender() == null) {
            throw new BadRequestException("Não foi fornecido quem enviou a mensagem");
        }
        if(messageDTO.getText().isEmpty()) {
            throw new BadRequestException("Mensagem em branco");
        }

        Optional<User> senderUserOptional = userRepository.findById(messageDTO.getSender().getId());
        if(senderUserOptional.isEmpty()) {
            throw new BadRequestException("Usuário não encontrado");
        }

        User loggedUser = userService.getLoggedUser();
        if(senderUserOptional.get().getId() != loggedUser.getId()) {
            throw new BadRequestException("Sender não é o usuário logado");
        }

        Optional<Chat> chatOptional = chatRepository.findById(messageDTO.getChat().getId());
        if(chatOptional.isEmpty()) {
            throw new BadRequestException("Não foi encontrado o chat");
        }

        Message message = new Message(messageDTO);
        message.setSender(loggedUser);
        message.setChat(chatOptional.get());
        message.setCreatedAt(LocalDateTime.now());

        Message savedMessage = messageRepository.save(message);

        webSocketService.sendNewMessage(savedMessage, savedMessage.getChat().getId().toString());
        webSocketService.notifyChatsUsers(message.getChat().getFromUser(), message.getChat().getToUser());

        return ResponseEntity.ok(new MessageDTO(savedMessage));
    }
}
