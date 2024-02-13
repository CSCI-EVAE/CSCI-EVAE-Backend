package fr.ubo.dosi.projectagile.cscievaebackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "rubrique_evaluation" )
public class RubriqueEvaluation {
    @Id
    @Column(name = "id_rubrique_evaluation", nullable = false)
    private Long id;

    @Size(max = 64)
    @Column(name = "designation", length = 64)
    private String designation;

    @NotNull
    @Column(name = "ordre", nullable = false)
    private Byte ordre;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_evaluation", nullable = false)
    private Evaluation idEvaluation;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_rubrique")
    private Rubrique idRubrique;

}