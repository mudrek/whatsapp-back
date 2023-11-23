package br.com.kydrem.whatsapp.chat;

import br.com.kydrem.whatsapp.core.exceptions.BadRequestException;
import br.com.kydrem.whatsapp.messages.Message;
import br.com.kydrem.whatsapp.messages.MessageDTO;
import br.com.kydrem.whatsapp.messages.MessageRepository;
import br.com.kydrem.whatsapp.user.User;
import br.com.kydrem.whatsapp.user.UserRepository;
import br.com.kydrem.whatsapp.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChatService {
    @Autowired
    ChatRepository chatRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    UserService userService;

    public ResponseEntity<ChatDTO> createChat(ChatDTO chatDTO) {
        if (chatDTO.getToUser() == null) {
            throw new BadRequestException("Ids de usuários inválidos");
        }

        User loggedUser = userService.getLoggedUser();

        if (chatDTO.getToUser().getId() == loggedUser.getId()) {
            throw new BadRequestException("Não é possível criar um chat consigo mesmo");
        }

        Chat chat = new Chat(chatDTO);

        chat.setFromUser(loggedUser);

        Optional<User> fromUserOptional = userRepository.findById(chat.getFromUser().getId());
        if (fromUserOptional.isEmpty()) {
            throw new BadRequestException("Não foi possível encontrar fromUser");
        }

        Optional<User> toUserOptional = userRepository.findById(chat.getToUser().getId());
        if (toUserOptional.isEmpty()) {
            throw new BadRequestException("Não foi possível encontrar toUser");
        }

        chat.setFromUser(fromUserOptional.get());
        chat.setToUser(toUserOptional.get());

        Optional<Chat> existingChatOptional = chatRepository.findByFromUserIdAndToUserId(chat.getFromUser().getId(),
                chat.getToUser().getId());
        Optional<Chat> existingInverseChatOptional = chatRepository
                .findByFromUserIdAndToUserId(chat.getToUser().getId(), chat.getFromUser().getId());

        if (existingChatOptional.isPresent() || existingInverseChatOptional.isPresent()) {
            throw new BadRequestException("Este chat já existe");
        }

        Chat savedChat = chatRepository.save(chat);

        return ResponseEntity.ok(new ChatDTO(savedChat));
    }

    public List<ChatDTO> findAllChatFromUser(User user) {
        User feedUser;
        if (user != null) {
            feedUser = user;
        } else {
            feedUser = userService.getLoggedUser();
        }

        List<Chat> allChatsFromUser = chatRepository.findAllChatByUserId(feedUser.getId());

        List<ChatDTO> allChatsDTOFromUser = allChatsFromUser.stream().map((chat) -> {
            ChatDTO chatDTO = new ChatDTO(chat);

            Optional<Message> lastMessageSendOptional = messageRepository.lastMessageSendFromChat(chatDTO.getId());
            if (lastMessageSendOptional.isPresent()) {
                chatDTO.setLastMessage(new MessageDTO(lastMessageSendOptional.get()));
            }

            return chatDTO;

        }).collect(Collectors.toList());

        return allChatsDTOFromUser;
    }
}
