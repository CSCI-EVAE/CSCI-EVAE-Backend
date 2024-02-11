package fr.ubo.dosi.projectagile.cscievaebackend.DTO;

import fr.ubo.dosi.projectagile.cscievaebackend.model.Role;
import fr.ubo.dosi.projectagile.cscievaebackend.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class UserDTO {
    private long id;
    private String username;
    private List<String> roles;
    private String email;
    private String nom;
    private String prenom;

    public UserDTO(User actualUser) {
        this.id = actualUser.getId();
        this.username = actualUser.getEmail();
        this.email = actualUser.getEmail();
        this.nom = actualUser.getLastName();
        this.prenom = actualUser.getFirstName();
        this.roles = actualUser.getRoles().stream().map(Role::getName).toList();
    }
}
