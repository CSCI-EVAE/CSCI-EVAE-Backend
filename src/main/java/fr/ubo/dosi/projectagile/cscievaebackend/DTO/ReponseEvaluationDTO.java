package fr.ubo.dosi.projectagile.cscievaebackend.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReponseEvaluationDTO {

    private Integer id;
    @NotNull
    private Integer idEvaluationId;
    private String noEtudiant;
    private String commentaire;
    @Size(max = 32)
    private String nom;
    @Size(max = 32)
    private String prenom;
    @NotNull
    private Set<ReponseQuestionDto> reponseQuestions = new LinkedHashSet<>();
    /**
     * DTO for {@link fr.ubo.dosi.projectagile.cscievaebackend.model.ReponseQuestion}
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReponseQuestionDto implements Serializable {
        private QuestionEvaluationDTO idQuestionEvaluation;
        private Long positionnement;
    }
}
