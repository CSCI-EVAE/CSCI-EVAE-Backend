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
@Table(name = "evaluation" )
public class Evaluation {
    @Id
    @Column(name = "id_evaluation", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "debut_reponse", nullable = false)
    private Instant debutReponse;

    @Size(max = 3)
    @NotNull
    @Column(name = "etat", nullable = false, length = 3)
    private String etat;

    @NotNull
    @Column(name = "fin_reponse", nullable = false)
    private Instant finReponse;

    @NotNull
    @Column(name = "no_evaluation", nullable = false)
    private Byte noEvaluation;

    @Size(max = 64)
    @Column(name = "periode", length = 64)
    private String periode;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "annee_pro", nullable = false)
    private Promotion anneePro;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumns({
            @JoinColumn(name = "code_ec", referencedColumnName = "code_ec", nullable = false),
            @JoinColumn(name = "code_formation", referencedColumnName = "code_formation", nullable = false),
            @JoinColumn(name = "code_ue", referencedColumnName = "code_ue", nullable = false)
    })
    private ElementConstitutif elementConstitutif;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "no_enseignant", nullable = false)
    private Enseignant noEnseignant;

}