package fr.ubo.dosi.projectagile.cscievaebackend.DTO;

import fr.ubo.dosi.projectagile.cscievaebackend.Model.Authentification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class UserDTO {
    private long id;
    private String username;
    private String role;



    public UserDTO(Authentification actualUser) {
        this.id = actualUser.getId();
        this.username = actualUser.getLoginConnection();
        this.role = actualUser.getRole();
    }
}
