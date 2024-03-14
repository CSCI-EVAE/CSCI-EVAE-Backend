package fr.ubo.dosi.projectagile.cscievaebackend.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.ubo.dosi.projectagile.cscievaebackend.model.EtudiantDto;
import fr.ubo.dosi.projectagile.cscievaebackend.model.ReponseQuestion;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * DTO for {@link fr.ubo.dosi.projectagile.cscievaebackend.model.ReponseEvaluation}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReponseEvaluationDto implements Serializable {
    private Integer id;
    @JsonIgnore
    private EtudiantDto noEtudiant;
    @Size(max = 512)
    private String commentaire;
    @Size(max = 32)
    private String nom;
    @Size(max = 32)
    private String prenom;
    private Set<ReponseQuestion> reponseQuestions = new LinkedHashSet<>();
}