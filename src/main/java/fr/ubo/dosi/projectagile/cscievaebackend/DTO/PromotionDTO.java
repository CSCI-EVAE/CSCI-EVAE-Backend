package fr.ubo.dosi.projectagile.cscievaebackend.DTO;

import fr.ubo.dosi.projectagile.cscievaebackend.model.Formation;
import lombok.*;

import java.time.LocalDate;
@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PromotionDTO {
    private String codeFormation;
    private String anneeUniversitaire;
    private String siglePromotion;
    private Short nbMaxEtudiant;
    private LocalDate dateReponseLP;
    private LocalDate dateReponseLALP;
    private LocalDate dateRentree;
    private String lieuRentree;
    private String processusStage;
    private String commentaire;
}
