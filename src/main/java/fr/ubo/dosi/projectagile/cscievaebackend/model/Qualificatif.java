package fr.ubo.dosi.projectagile.cscievaebackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "qualificatif" )
public class Qualificatif {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_qualificatif", nullable = false)
    private Long id;

    @Size(max = 16)
    @NotNull
    @Column(name = "maximal", nullable = false, length = 16)
    private String maximal;

    @Size(max = 16)
    @NotNull
    @Column(name = "minimal", nullable = false, length = 16)
    private String minimal;

}