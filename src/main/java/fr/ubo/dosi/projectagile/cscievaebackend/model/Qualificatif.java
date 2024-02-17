package fr.ubo.dosi.projectagile.cscievaebackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "QUALIFICATIF")
public class Qualificatif {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "QUA_SEQ"
    )
    @SequenceGenerator(
            name = "QUA_SEQ",
            sequenceName = "QUA_SEQ",
            allocationSize = 1
    )
    @Column(name = "ID_QUALIFICATIF", nullable = false)
    private Integer id;

    @Size(max = 16)
    @NotNull
    @Column(name = "MAXIMAL", nullable = false, length = 16)
    private String maximal;

    @Size(max = 16)
    @NotNull
    @Column(name = "MINIMAL", nullable = false, length = 16)
    private String minimal;

}