package br.com.kydrem.whatsapp.user.model;

import br.com.kydrem.whatsapp.user.dto.UserDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String email;
    private String username;
    private String password;

    public User(UserDTO userDTO) {
        this.id = userDTO.getId();
        this.name = userDTO.getName();
        this.email = userDTO.getEmail();
        this.username = userDTO.getUsername();
        this.password = userDTO.getPassword();
    }
}
