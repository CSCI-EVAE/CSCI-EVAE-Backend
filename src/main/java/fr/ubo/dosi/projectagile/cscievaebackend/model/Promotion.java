package fr.ubo.dosi.projectagile.cscievaebackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "PROMOTION")
@ToString
public class Promotion {
    @EmbeddedId
    private PromotionId id;

    @MapsId("codeFormation")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "CODE_FORMATION", nullable = false)
    @ToString.Exclude
    private Formation codeFormation;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "NO_ENSEIGNANT")
    @ToString.Exclude
    private Enseignant noEnseignant;

    @Size(max = 16)
    @Column(name = "SIGLE_PROMOTION", length = 16)
    private String siglePromotion;

    @NotNull
    @Column(name = "NB_MAX_ETUDIANT", nullable = false)
    private Short nbMaxEtudiant;

    @Column(name = "DATE_REPONSE_LP")
    private LocalDate dateReponseLp;

    @Column(name = "DATE_REPONSE_LALP")
    private LocalDate dateReponseLalp ;

    @Column(name = "DATE_RENTREE")
    private LocalDate dateRentree;

    @Size(max = 12)
    @Column(name = "LIEU_RENTREE", length = 12)
    private String lieuRentree;

    @Size(max = 5)
    @Column(name = "PROCESSUS_STAGE", length = 5)
    private String processusStage;

    @Size(max = 255)
    @Column(name = "COMMENTAIRE")
    private String commentaire;

    @OneToMany(mappedBy = "promotion")
    @ToString.Exclude
    private Set<Evaluation> evaluations = new LinkedHashSet<>();

    @OneToMany(mappedBy = "promotion")
    @OrderBy("nom")
    private Set<Etudiant> etudiants = new LinkedHashSet<>();

}