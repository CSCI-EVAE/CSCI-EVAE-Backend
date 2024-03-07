package fr.ubo.dosi.projectagile.cscievaebackend.DTO;

import fr.ubo.dosi.projectagile.cscievaebackend.model.Evaluation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UniteEnseignementDTO {
    private Integer evaluationId;
    private String etat;
    private LocalDate debutReponse;
    private LocalDate finReponse;
    private boolean EvaExiste;
    private String codeFormation;
    private String codeUe;
    private String codeEc;
    private String designation;
    private Long nbhCm;
    private Short nbhTd;
    private Short nbhTp;
    private Double totaleHeures = 0.0;

    public void setEvaExiste(Evaluation evaluation) {
        if (evaluation != null) {
            this.EvaExiste = true;
            this.evaluationId = evaluation.getId();
            this.etat = evaluation.getEtat();
            this.debutReponse = evaluation.getDebutReponse();
            this.finReponse = evaluation.getFinReponse();
        } else {
            this.EvaExiste = false;
        }
    }

    public void setTotalHeures() {
        this.totaleHeures=nbhCm*1.5+nbhTd+nbhTp;
    }

}
