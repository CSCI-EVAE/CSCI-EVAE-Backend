package fr.ubo.dosi.projectagile.cscievaebackend.DTO;

import lombok.*;

import java.math.BigDecimal;
@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RubriqueQuestionDTO {
    private Long idQuestion;
    private QuestionDTO idQuestion;
    private String designation;

}
