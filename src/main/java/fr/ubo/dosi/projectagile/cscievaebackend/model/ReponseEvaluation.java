package fr.ubo.dosi.projectagile.cscievaebackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "reponse_evaluation" )
public class ReponseEvaluation {
    @Id
    @Column(name = "id_reponse_evaluation", nullable = false)
    private Long id;

    @Size(max = 512)
    @Column(name = "commentaire", length = 512)
    private String commentaire;

    @Size(max = 32)
    @Column(name = "nom", length = 32)
    private String nom;

    @Size(max = 32)
    @Column(name = "prenom", length = 32)
    private String prenom;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_evaluation", nullable = false)
    private Evaluation idEvaluation;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "no_etudiant_nat")
    private Etudiant noEtudiantNat;

}