package fr.ubo.dosi.projectagile.cscievaebackend.DTO;


import lombok.*;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionDTO {
    private Integer id;
    private QualificatifDTO idQualificatif;
    private String type;
    private String intitule;
}
