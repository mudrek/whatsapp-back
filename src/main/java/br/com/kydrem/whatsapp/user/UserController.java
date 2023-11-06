package br.com.kydrem.whatsapp.user;

import br.com.kydrem.whatsapp.core.authentication.AuthDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<UserDTO> login(@RequestBody AuthDTO.LoginRequest userLogin) {
        return userService.login(userLogin);
    }

    @GetMapping("/searchUser")
    public ResponseEntity<UserDTO> searchByUsername(@RequestParam(name = "username") String username) {
        return userService.findByUsername(username);
    }
}
