package fr.ubo.dosi.projectagile.cscievaebackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link ElementConstitutifId}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ElementConstitutifIdDto implements Serializable {
    private String codeEc;
    private String codeFormation;
    private String codeUe;
}