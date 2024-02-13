package fr.ubo.dosi.projectagile.cscievaebackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;

@Getter
@Setter
@Entity
@ToString
@Table(name = "etudiant")
public class Etudiant {
    @Id
    @Size(max = 50)
    @Column(name = "no_etudiant_nat", nullable = false, length = 50)
    private String noEtudiantNat;

    @Column(name = "abandon_date")
    private Instant abandonDate;

    @Size(max = 255)
    @Column(name = "abandon_motif")
    private String abandonMotif;

    @Size(max = 255)
    @Column(name = "actu_adresse")
    private String actuAdresse;

    @Size(max = 10)
    @Column(name = "actu_cp", length = 10)
    private String actuCp;

    @Size(max = 255)
    @Column(name = "actu_pays")
    private String actuPays;

    @Size(max = 255)
    @Column(name = "actu_ville")
    private String actuVille;

    @Size(max = 10)
    @Column(name = "code_com", length = 10)
    private String codeCom;

    @Size(max = 10)
     
    @Column(name = "compte_cri", nullable = false, length = 10)
    private String compteCri;

     
    @Column(name = "date_naissance", nullable = false)
    private Instant dateNaissance;

    @Size(max = 255)
     
    @Column(name = "dernier_diplome", nullable = false)
    private String dernierDiplome;

    @Size(max = 255)
    @Column(name = "email")
    private String email;

    @Column(name = "est_diplome")
    private Character estDiplome;

    @Column(name = "grpe_anglais")
    private Integer grpeAnglais;

    @Size(max = 255)
     
    @Column(name = "lieu_naissance", nullable = false)
    private String lieuNaissance;

    @Size(max = 50)
     
    @Column(name = "nationalite", nullable = false, length = 50)
    private String nationalite;

    @Size(max = 20)
    @Column(name = "no_etudiant_ubo", length = 20)
    private String noEtudiantUbo;

    @Size(max = 50)
     
    @Column(name = "nom", nullable = false, length = 50)
    private String nom;

    @Size(max = 255)
     
    @Column(name = "perm_adresse", nullable = false)
    private String permAdresse;

    @Size(max = 10)
     
    @Column(name = "perm_cp", nullable = false, length = 10)
    private String permCp;

    @Size(max = 255)
     
    @Column(name = "perm_pays", nullable = false)
    private String permPays;

    @Size(max = 255)
     
    @Column(name = "perm_ville", nullable = false)
    private String permVille;

    @Size(max = 50)
     
    @Column(name = "prenom", nullable = false, length = 50)
    private String prenom;

    @Size(max = 1)
     
    @Column(name = "sexe", nullable = false, length = 1)
    private String sexe;

    @Size(max = 3)
     
    @Column(name = "sigle_etu", nullable = false, length = 3)
    private String sigleEtu;

    @Size(max = 3)
     
    @Column(name = "situation", nullable = false, length = 3)
    private String situation;

    @Size(max = 20)
    @Column(name = "tel_fixe", length = 20)
    private String telFixe;

    @Size(max = 20)
    @Column(name = "tel_port", length = 20)
    private String telPort;

    @Size(max = 255)
    @Column(name = "ubo_email")
    private String uboEmail;

    @Size(max = 255)
     
    @Column(name = "universite", nullable = false)
    private String universite;

     
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "annee_pro", nullable = false)
    @ToString.Exclude
    private Promotion anneePro;

}