package fr.ubo.dosi.projectagile.cscievaebackend.DTO;

import lombok.Data;

import java.util.List;

@Data
public class IncomingRubriqueQuestionDTO {
    private Long idRubrique;
    private List<Long> questionIds; // Assuming these are IDs of questions
    private Long ordre;
}