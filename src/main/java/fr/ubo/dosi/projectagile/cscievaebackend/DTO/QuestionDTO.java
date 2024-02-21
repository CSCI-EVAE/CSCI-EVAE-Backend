package fr.ubo.dosi.projectagile.cscievaebackend.DTO;

import fr.ubo.dosi.projectagile.cscievaebackend.model.Qualificatif;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionDTO {
    @NotNull
    private QualificatifDTO idQualificatif;
    @NotNull
    @Size(max = 10)
    private String type;
    private Integer id;
    @NotNull
    @Size(max = 64)
    private String intitule;
}
