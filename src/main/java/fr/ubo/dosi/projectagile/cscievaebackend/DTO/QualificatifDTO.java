package fr.ubo.dosi.projectagile.cscievaebackend.DTO;

import lombok.*;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QualificatifDTO {

    private Integer id;
    private String minimal;

    private String maximal;

}
