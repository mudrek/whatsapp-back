package br.com.kydrem.whatsapp.messages;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @PostMapping
    public ResponseEntity<MessageDTO> sendMessage(@Valid @RequestBody MessageDTO messageDTO) {
        return messageService.sendMessage(messageDTO);
    }

    @GetMapping
    public ResponseEntity<List<MessageDTO>> findAllMessageFromChatAndUser(@RequestParam(name = "chatId") Long chatId) {
        return ResponseEntity.ok(messageService.findAllMessageFromChatAndUser(chatId));
    }
}
