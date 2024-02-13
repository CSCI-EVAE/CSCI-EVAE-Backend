package fr.ubo.dosi.projectagile.cscievaebackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "rubrique" )
public class Rubrique {
    @Id
    @Column(name = "id_rubrique", nullable = false)
    private Long id;

    @Size(max = 32)
    @NotNull
    @Column(name = "designation", nullable = false, length = 32)
    private String designation;

    @Column(name = "ordre")
    private Double ordre;

    @Size(max = 10)
    @NotNull
    @Column(name = "type", nullable = false, length = 10)
    private String type;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "no_enseignant")
    private Enseignant noEnseignant;

}