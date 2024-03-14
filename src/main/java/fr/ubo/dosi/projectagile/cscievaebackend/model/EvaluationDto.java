package fr.ubo.dosi.projectagile.cscievaebackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link Evaluation}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EvaluationDto implements Serializable {
    private Integer id;
}