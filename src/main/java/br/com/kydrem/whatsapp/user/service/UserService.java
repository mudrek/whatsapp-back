package br.com.kydrem.whatsapp.user.service;

import br.com.kydrem.whatsapp.core.exceptions.BadRequestException;
import br.com.kydrem.whatsapp.user.dto.UserDTO;
import br.com.kydrem.whatsapp.user.model.User;
import br.com.kydrem.whatsapp.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<UserDTO> saveUser(UserDTO userDTO){
        List<User> verifyRegisteredAccounts = userRepository.findByUsernameOrEmail(userDTO.getUsername(), userDTO.getEmail());
        if(!verifyRegisteredAccounts.isEmpty()) {
            throw new BadRequestException("Email ou Username já está sendo utilizado");
        }
        User user = new User(userDTO);

        User savedUser = userRepository.save(user);

        return ResponseEntity.ok(new UserDTO(savedUser));
    }
}
