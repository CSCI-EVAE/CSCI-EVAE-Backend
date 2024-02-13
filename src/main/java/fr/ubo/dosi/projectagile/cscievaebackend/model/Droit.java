package fr.ubo.dosi.projectagile.cscievaebackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "droit" )
public class Droit {
    @EmbeddedId
    private DroitId id;

    @MapsId("idEvaluation")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "ID_EVALUATION", nullable = false)
    private Evaluation idEvaluation;

    @MapsId("noEnseignant")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "NO_ENSEIGNANT", nullable = false)
    private Enseignant noEnseignant;

    @NotNull
    @Column(name = "consultation", nullable = false)
    private Character consultation;

    @NotNull
    @Column(name = "duplication", nullable = false)
    private Character duplication;

}