package fr.ubo.dosi.projectagile.cscievaebackend.DTO;

import lombok.Data;

import java.util.Map;

@Data
public class IncomingRubriqueQuestionDTO {
    private Long idRubrique;
    private Map<Long,Long> questionIds;
    private Long ordre;
}