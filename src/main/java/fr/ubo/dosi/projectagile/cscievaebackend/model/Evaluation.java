package fr.ubo.dosi.projectagile.cscievaebackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "EVALUATION")
public class Evaluation {
    @Id
    @Column(name = "ID_EVALUATION", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "NO_ENSEIGNANT", nullable = false)
    private Enseignant noEnseignant;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumns({
            @JoinColumn(name = "CODE_FORMATION", referencedColumnName = "CODE_FORMATION", nullable = false, insertable = false, updatable = false),
            @JoinColumn(name = "CODE_UE", referencedColumnName = "CODE_UE", nullable = false, insertable = false, updatable = false),
            @JoinColumn(name = "CODE_EC", referencedColumnName = "CODE_EC", nullable = false, insertable = false, updatable = false)
    })
    @OnDelete(action = OnDeleteAction.RESTRICT)
    private ElementConstitutif elementConstitutif;

    @NotNull
    @Column(name = "NO_EVALUATION", nullable = false)
    private Short noEvaluation;

    @Size(max = 16)
    @NotNull
    @Column(name = "DESIGNATION", nullable = false, length = 16)
    private String designation;

    @Size(max = 3)
    @NotNull
    @Column(name = "ETAT", nullable = false, length = 3)
    private String etat;

    @Size(max = 64)
    @Column(name = "PERIODE", length = 64)
    private String periode;

    @NotNull
    @Column(name = "DEBUT_REPONSE", nullable = false)
    private LocalDate debutReponse;

    @NotNull
    @Column(name = "FIN_REPONSE", nullable = false)
    private LocalDate finReponse;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumns({
            @JoinColumn(name = "CODE_FORMATION", referencedColumnName = "CODE_FORMATION", nullable = false, insertable = false, updatable = false),
            @JoinColumn(name = "ANNEE_UNIVERSITAIRE", referencedColumnName = "ANNEE_UNIVERSITAIRE", nullable = false, insertable = false, updatable = false)
    })
    @OnDelete(action = OnDeleteAction.RESTRICT)
    private Promotion promotion;

    @OneToMany(mappedBy = "idEvaluation")
    @OrderColumn(name = "ORDRE")
    private Set<RubriqueEvaluation> rubriqueEvaluations = new LinkedHashSet<>();

    @NotNull
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumns({
            @JoinColumn(name = "CODE_FORMATION", referencedColumnName = "CODE_FORMATION", nullable = false, insertable = false, updatable = false),
            @JoinColumn(name = "CODE_UE", referencedColumnName = "CODE_UE", nullable = false, insertable = false, updatable = false),
    })
    private UniteEnseignement uniteEnseignement;

}