package fr.ubo.dosi.projectagile.cscievaebackend.DTO;

import lombok.*;

import java.util.List;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RubriqueQuestionSaveDTO {
    private String designation;
    private List<QuestionDTO> questions;
    private Long ordre;
}
