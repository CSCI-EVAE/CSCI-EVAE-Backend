package fr.ubo.dosi.projectagile.cscievaebackend.DTO;

import lombok.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;


@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EvaluationDTO  {
    private Integer id;
    @NotNull
    @Size(max = 16)
    private String designation;
    @NotNull
    private LocalDate debutReponse;
    @NotNull
    private LocalDate finReponse;
    private List<RubriqueEvaluationDTO> rubriqueEvaluations;
}
