package fr.ubo.dosi.projectagile.cscievaebackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "ENSEIGNANT")
public class Enseignant {
    @Id
    @Column(name = "NO_ENSEIGNANT", nullable = false)
    private Short id;

    @Size(max = 5)
    @NotNull
    @Column(name = "TYPE", nullable = false, length = 5)
    private String type;

    @Size(max = 1)
    @NotNull
    @Column(name = "SEXE", nullable = false, length = 1)
    private String sexe;

    @Size(max = 50)
    @NotNull
    @Column(name = "NOM", nullable = false, length = 50)
    private String nom;

    @Size(max = 50)
    @NotNull
    @Column(name = "PRENOM", nullable = false, length = 50)
    private String prenom;

    @Size(max = 255)
    @NotNull
    @Column(name = "ADRESSE", nullable = false)
    private String adresse;

    @Size(max = 10)
    @NotNull
    @Column(name = "CODE_POSTAL", nullable = false, length = 10)
    private String codePostal;

    @Size(max = 255)
    @NotNull
    @Column(name = "VILLE", nullable = false)
    private String ville;

    @Size(max = 5)
    @NotNull
    @Column(name = "PAYS", nullable = false, length = 5)
    private String pays;

    @Size(max = 20)
    @NotNull
    @Column(name = "MOBILE", nullable = false, length = 20)
    private String mobile;

    @Size(max = 20)
    @Column(name = "TELEPHONE", length = 20)
    private String telephone;

    @Size(max = 255)
    @NotNull
    @Column(name = "EMAIL_UBO", nullable = false)
    private String emailUbo;

    @Size(max = 255)
    @Column(name = "EMAIL_PERSO")
    private String emailPerso;

    @JsonIgnore
    @OneToMany(mappedBy = "noEnseignant")
    private Set<Evaluation> evaluations = new LinkedHashSet<>();
    @JsonIgnore
    @OneToMany(mappedBy = "noEnseignant")
    private Set<Promotion> promotions = new LinkedHashSet<>();
    @JsonIgnore
    @OneToMany(mappedBy = "noEnseignant")
    @OrderBy("designation DESC")
    private Set<UniteEnseignement> uniteEnseignements = new LinkedHashSet<>();

}