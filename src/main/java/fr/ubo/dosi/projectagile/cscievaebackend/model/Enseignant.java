package fr.ubo.dosi.projectagile.cscievaebackend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "enseignant" )
public class Enseignant {
    @Id
    @Column(name = "no_enseignant", nullable = false)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Column(name = "adresse", nullable = false)
    private String adresse;

    @Size(max = 10)
    @NotNull
    @Column(name = "cp", nullable = false, length = 10)
    private String cp;

    @Size(max = 255)
    @Column(name = "enc_perso_email")
    private String encPersoEmail;

    @Size(max = 20)
    @Column(name = "enc_perso_tel", length = 20)
    private String encPersoTel;

    @Size(max = 255)
    @Column(name = "enc_ubo_email")
    private String encUboEmail;

    @Size(max = 20)
    @Column(name = "enc_ubo_tel", length = 20)
    private String encUboTel;

    @Size(max = 50)
    @Column(name = "int_fonction", length = 50)
    private String intFonction;

    @Size(max = 50)
    @Column(name = "int_no_insee", length = 50)
    private String intNoInsee;

    @Size(max = 255)
    @Column(name = "int_prof_email")
    private String intProfEmail;

    @Size(max = 20)
    @Column(name = "int_prof_tel", length = 20)
    private String intProfTel;

    @Size(max = 255)
    @Column(name = "int_soc_adresse")
    private String intSocAdresse;

    @Size(max = 10)
    @Column(name = "int_soc_cp", length = 10)
    private String intSocCp;

    @Size(max = 50)
    @Column(name = "int_soc_nom", length = 50)
    private String intSocNom;

    @Size(max = 255)
    @Column(name = "int_soc_pays")
    private String intSocPays;

    @Size(max = 255)
    @Column(name = "int_soc_ville")
    private String intSocVille;

    @Size(max = 50)
    @NotNull
    @Column(name = "nom", nullable = false, length = 50)
    private String nom;

    @Size(max = 255)
    @NotNull
    @Column(name = "pays", nullable = false)
    private String pays;

    @Size(max = 50)
    @NotNull
    @Column(name = "prenom", nullable = false, length = 50)
    private String prenom;

    @Size(max = 1)
    @NotNull
    @Column(name = "sexe", nullable = false, length = 1)
    private String sexe;

    @Size(max = 20)
    @Column(name = "tel_port", length = 20)
    private String telPort;

    @Size(max = 10)
    @NotNull
    @Column(name = "type", nullable = false, length = 10)
    private String type;

    @Size(max = 255)
    @NotNull
    @Column(name = "ville", nullable = false)
    private String ville;

}