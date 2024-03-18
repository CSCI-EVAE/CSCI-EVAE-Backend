package fr.ubo.dosi.projectagile.cscievaebackend.DTO;


import lombok.*;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RubriqueDTO {
    private Integer id;
    private String type;
    private String designation;
    private Long ordre;
    private Set<RubriqueQuestionDTO> rubriqueQuestions = new LinkedHashSet<>();
    public List<RubriqueQuestionDTO> getRubriqueQuestions() {
        return rubriqueQuestions.stream()
                .sorted(Comparator.comparing(RubriqueQuestionDTO::getOrdre))
                .collect(Collectors.toList());
    }

}
