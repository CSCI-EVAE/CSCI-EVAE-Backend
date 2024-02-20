package fr.ubo.dosi.projectagile.cscievaebackend.DTO;

import fr.ubo.dosi.projectagile.cscievaebackend.model.Qualificatif;
import lombok.*;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionDTO {
    private QualificatifDTO idQualificatif;
    private String type;
    private Integer id;
    private String intitule;
}
