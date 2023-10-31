package br.com.kydrem.whatsapp.user.controller;

import br.com.kydrem.whatsapp.core.authentication.AuthDTO;
import br.com.kydrem.whatsapp.user.dto.UserDTO;
import br.com.kydrem.whatsapp.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> saveUser(@Valid @RequestBody UserDTO userDTO) {
        return userService.saveUser(userDTO);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthDTO.LoginRequest userLogin) {
        return userService.login(userLogin);
    }
}
