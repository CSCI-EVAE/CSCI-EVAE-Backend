package fr.ubo.dosi.projectagile.cscievaebackend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "formation")
public class Formation {
    @Id
    @Size(max = 8)
    @Column(name = "code_formation", nullable = false, length = 8)
    private String codeFormation;

    @Column(name = "debut_habilitation")
    private Instant debutHabilitation;

    @Size(max = 3)
    @NotNull
    @Column(name = "diplome", nullable = false, length = 3)
    private String diplome;

    @NotNull
    @Column(name = "double_diplome", nullable = false)
    private Character doubleDiplome;

    @Column(name = "fin_habilitation")
    private Instant finHabilitation;

    @NotNull
    @Column(name = "n0_annee", nullable = false)
    private Byte n0Annee;

    @Size(max = 64)
    @NotNull
    @Column(name = "nom_formation", nullable = false, length = 64)
    private String nomFormation;

}