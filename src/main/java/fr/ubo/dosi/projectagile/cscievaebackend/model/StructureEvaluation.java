package fr.ubo.dosi.projectagile.cscievaebackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "structure_evaluation" )
public class StructureEvaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "no_evaluation", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "date_creation", nullable = false)
    private Instant dateCreation;

    @Size(max = 3)
    @NotNull
    @Column(name = "etat", nullable = false, length = 3)
    private String etat;

}