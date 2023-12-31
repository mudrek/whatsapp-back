package br.com.kydrem.whatsapp.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String username;
    private String password;

    public UserDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.username = user.getUsername();
    }

    public static UserDTO basicInfo(User user) {
        return new UserDTO(user.getId(),
                user.getName(),
                null,
                user.getUsername(),
                null
        );
    }

}
