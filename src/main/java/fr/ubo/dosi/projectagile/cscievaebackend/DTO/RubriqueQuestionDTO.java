package fr.ubo.dosi.projectagile.cscievaebackend.DTO;


import lombok.*;
@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RubriqueQuestionDTO {
    private QuestionDTO idQuestion ;
    private Long ordre;
}
