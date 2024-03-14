package fr.ubo.dosi.projectagile.cscievaebackend.model;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link Etudiant}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EtudiantDto implements Serializable {
    @Size(max = 50)
    private String noEtudiant;
}