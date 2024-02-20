package fr.ubo.dosi.projectagile.cscievaebackend.DTO;

import fr.ubo.dosi.projectagile.cscievaebackend.model.Qualificatif;
import lombok.Data;

@Data
public class QuestionDTO {
    private Integer id;
    private String type;
    private Qualificatif idQualificatif;
}
