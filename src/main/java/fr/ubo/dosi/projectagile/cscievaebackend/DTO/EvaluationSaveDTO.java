package fr.ubo.dosi.projectagile.cscievaebackend.DTO;

import fr.ubo.dosi.projectagile.cscievaebackend.model.Droit;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Enseignant;
import fr.ubo.dosi.projectagile.cscievaebackend.model.ElementConstitutif;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Promotion;
import fr.ubo.dosi.projectagile.cscievaebackend.model.ReponseEvaluation;
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
public class EvaluationSaveDTO {
    private String nomFormation;
    private String anneePro;
    private String codeUE;
    private String codeEC;
    private String designation;
    private LocalDate debutReponse;
    private LocalDate finReponse;
    private List<RubriqueQuestionSaveDTO> RubriqueQuestion;
}
 /*
        nomFormation,
        anneePro,
        codeUE,
        codeEC,
        designation,
        dateDebut,
        dateFin,
        Rubrique[
            {
            	désignation,
            	ordre,
            	Question[
            	{ intitulé,
            	ordre,
            	}
            	]
            }
          ]*/