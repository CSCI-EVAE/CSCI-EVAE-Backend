package fr.ubo.dosi.projectagile.cscievaebackend.DTO;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class AuthRequestDTO {

    private String username;
    private String password;
}