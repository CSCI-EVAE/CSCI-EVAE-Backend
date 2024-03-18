package fr.ubo.dosi.projectagile.cscievaebackend.DTO;

import lombok.*;

import java.time.LocalDate;
@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PromotionDTO {
    private String codeFormation;
    private String siglePromotion;
    private Short nbMaxEtudiant;
    private LocalDate dateReponseLp;
    private LocalDate dateReponseLalp;
    private LocalDate dateRentree;
    private String lieuRentree;
    private String processusStage;
    private String commentaire;
    private String anneeUniversitaire;
    private String nomResponsable;
    private String prenomResponsable;
}
