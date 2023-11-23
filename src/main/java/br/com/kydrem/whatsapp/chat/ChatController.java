package br.com.kydrem.whatsapp.chat;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
public class ChatController {
    @Autowired
    private ChatService chatService;

    @PostMapping
    public ResponseEntity<ChatDTO> createChat(@Valid @RequestBody ChatDTO chatDTO) {
        return chatService.createChat(chatDTO);
    }

    @GetMapping
    public ResponseEntity<List<ChatDTO>> getAllChatsFromUser() {
        return ResponseEntity.ok(chatService.findAllChatFromUser(null));
    }
}
