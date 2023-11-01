package br.com.kydrem.whatsapp.chat;

import br.com.kydrem.whatsapp.core.exceptions.BadRequestException;
import br.com.kydrem.whatsapp.user.User;
import br.com.kydrem.whatsapp.user.UserRepository;
import br.com.kydrem.whatsapp.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChatService {
    @Autowired
    ChatRepository chatRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;

    public ResponseEntity<ChatDTO> createChat(ChatDTO chatDTO) {
        if (chatDTO.getFromUser() == null || chatDTO.getToUser() == null) {
            throw new BadRequestException("Ids de usuários inválidos");
        }

        Chat chat = new Chat(chatDTO);

        User loggedUser = userService.getLoggedUser();
        if (loggedUser.getId() != chat.getFromUser().getId()) {
            throw new BadRequestException("Erro ao criar chat, o fromUser não corresponde ao usuário logado");
        }

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

        Optional<Chat> existingChatOptional = chatRepository.findByFromUserIdAndToUserId(chat.getFromUser().getId(), chat.getToUser().getId());
        Optional<Chat> existingInverseChatOptional = chatRepository.findByFromUserIdAndToUserId(chat.getToUser().getId(), chat.getFromUser().getId());

        if (existingChatOptional.isPresent() || existingInverseChatOptional.isPresent()) {
            throw new BadRequestException("Este chat já existe");
        }

        Chat savedChat = chatRepository.save(chat);

        return ResponseEntity.ok(new ChatDTO(savedChat));
    }
}
