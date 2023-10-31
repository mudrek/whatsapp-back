package br.com.kydrem.whatsapp.user.dto;

import br.com.kydrem.whatsapp.user.model.User;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserDTO {
    private Integer id;
    @NotBlank()
    private String name;
    @NotBlank
    private String email;
    @NotBlank
    private String username;
    @NotBlank
    private String password;

    public UserDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.username = user.getUsername();
    }

}
