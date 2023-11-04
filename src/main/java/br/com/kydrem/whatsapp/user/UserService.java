package br.com.kydrem.whatsapp.user;

import br.com.kydrem.whatsapp.core.authentication.AuthDTO;
import br.com.kydrem.whatsapp.core.authentication.AuthService;
import br.com.kydrem.whatsapp.core.authentication.AuthUser;
import br.com.kydrem.whatsapp.core.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthService authService;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    public ResponseEntity<UserDTO> saveUser(UserDTO userDTO) {
        List<User> verifyRegisteredAccounts = userRepository.findByUsernameOrEmail(userDTO.getUsername(), userDTO.getEmail());
        if (!verifyRegisteredAccounts.isEmpty()) {
            throw new BadRequestException("Email ou Username já está sendo utilizado");
        }
        User user = new User(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = userRepository.save(user);

        return ResponseEntity.ok(new UserDTO(savedUser));
    }

    public ResponseEntity login(AuthDTO.LoginRequest userLogin) {
        Authentication authentication =
                authenticationManager
                        .authenticate(new UsernamePasswordAuthenticationToken(
                                userLogin.username(),
                                userLogin.password()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        AuthUser userDetails = (AuthUser) authentication.getPrincipal();

        String token = authService.generateToken(authentication);

        return ResponseEntity.ok().header("Authorization", "Bearer " + token).body(new UserDTO(userDetails.getUser()));
    }

    public ResponseEntity<UserDTO> findByUsername(String username) {
        Optional<User> searchedUserOptional = userRepository.findByUsername(username);

        if (searchedUserOptional.isEmpty()) {
            throw new BadRequestException("Não foi possível encontrar um usuário com esse username");
        }

        return ResponseEntity.ok(UserDTO.basicInfo(searchedUserOptional.get()));
    }

    public User getLoggedUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<User> loggedUserOptional = userRepository.findByUsername(username);

        if (loggedUserOptional.isEmpty()) {
            throw new BadRequestException("Usuário não está logado");
        }

        return loggedUserOptional.get();
    }
}
