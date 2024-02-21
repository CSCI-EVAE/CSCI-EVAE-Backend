package fr.ubo.dosi.projectagile.cscievaebackend.DTO;

import fr.ubo.dosi.projectagile.cscievaebackend.model.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EvaluationSaveOrUpdateDTO {
    private Integer id;
    @NotNull
    @Size(max = 16)
    private String designation;
    @NotNull
    private LocalDate debutReponse;
    @NotNull
    private LocalDate finReponse;
    private List<RubriqueEvaluationDTO> rubriqueEvaluations;
    private List<QuestionEvaluationDTO> questionEvaluations;
    private List<ReponseEvaluation> reponseEvaluation;
    private List<Droit> droit;
    private String periode;
    private String etat;
    private Short noEvaluation;
    private Promotion idPromotion;
    private ElementConstitutif idElementConstitutif;
    private Enseignant idNoEnseignant;
}
