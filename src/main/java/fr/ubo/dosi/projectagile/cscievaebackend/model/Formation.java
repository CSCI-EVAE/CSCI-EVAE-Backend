package fr.ubo.dosi.projectagile.cscievaebackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@ToString
@Table(name = "FORMATION")
public class Formation {
    @Id
    @Size(max = 8)
    @Column(name = "CODE_FORMATION", nullable = false, length = 8)
    private String codeFormation;

    @Size(max = 3)
    @NotNull
    @Column(name = "DIPLOME", nullable = false, length = 3)
    private String diplome;

    @NotNull
    @Column(name = "N0_ANNEE", nullable = false)
    private Boolean n0Annee = false;

    @Size(max = 64)
    @NotNull
    @Column(name = "NOM_FORMATION", nullable = false, length = 64)
    private String nomFormation;

    @NotNull
    @Column(name = "DOUBLE_DIPLOME", nullable = false)
    private char doubleDiplome;

    @Column(name = "DEBUT_ACCREDITATION")
    private LocalDate debutAccreditation;

    @Column(name = "FIN_ACCREDITATION")
    private LocalDate finAccreditation;

    @OneToMany(mappedBy = "codeFormation")
    @JsonIgnore
    @ToString.Exclude
    private Set<Promotion> promotions = new LinkedHashSet<>();

    @OneToMany(mappedBy = "formation")
    @ToString.Exclude
    @JsonIgnore
    @OrderBy("designation ASC ")
    private Set<UniteEnseignement> uniteEnseignements = new LinkedHashSet<>();

}