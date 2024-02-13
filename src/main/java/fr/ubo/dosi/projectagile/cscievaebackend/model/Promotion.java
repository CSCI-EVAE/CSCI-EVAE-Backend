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
@Table(name = "promotion" )
public class Promotion {
    @Id
    @Size(max = 10)
    @Column(name = "annee_pro", nullable = false, length = 10)
    private String anneePro;

    @Size(max = 255)
    @Column(name = "commentaire")
    private String commentaire;

    @Column(name = "date_rentree")
    private Instant dateRentree;

    @Column(name = "date_reponse_lalp")
    private Instant dateReponseLalp;

    @Column(name = "date_reponse_lp")
    private Instant dateReponseLp;

    @Size(max = 3)
    @NotNull
    @Column(name = "etat_preselection", nullable = false, length = 3)
    private String etatPreselection;

    @Size(max = 255)
    @Column(name = "lieu_rentree")
    private String lieuRentree;

    @NotNull
    @Column(name = "nb_etu_souhaite", nullable = false)
    private Short nbEtuSouhaite;

    @Size(max = 5)
    @Column(name = "processus_stage", length = 5)
    private String processusStage;

    @Size(max = 5)
    @NotNull
    @Column(name = "sigle_pro", nullable = false, length = 5)
    private String siglePro;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "code_formation")
    private Formation codeFormation;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "no_enseignant")
    private Enseignant noEnseignant;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "no_evaluation")
    private StructureEvaluation noEvaluation;

}