package fr.ubo.dosi.projectagile.cscievaebackend.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EtudiantDTO {
    @NotBlank(message = "Le numéro étudiant ne peut pas être vide")
    @NotNull(message = "Le numéro étudiant ne peut pas être null")
    private String noEtudiant;

    @NotBlank(message = "Le nom ne peut pas être vide")
    @NotNull(message = "Le nom ne peut pas être null")
    private String nom;

    @NotBlank(message = "Le prénom ne peut pas être vide")
    @NotNull(message = "Le prénom ne peut pas être null")
    private String prenom;

    @NotBlank(message = "Le sexe ne peut pas être vide")
    @NotNull(message = "Le sexe ne peut pas être null")
    private String sexe;

    @NotNull(message = "La date de naissance ne peut pas être null")
    private LocalDate dateNaissance;

    @NotBlank(message = "Le lieu de naissance ne peut pas être vide")
    @NotNull(message = "Le lieu de naissance ne peut pas être null")
    private String lieuNaissance;

    @NotBlank(message = "La nationalite  ne peut pas être vide")
    @NotNull(message = "La nationalite  ne peut pas être null")
    private String nationalite;

    @NotBlank(message = "Le telephone  ne peut pas être vide")
    @NotNull(message = "Le telephone  ne peut pas être null")
    private String telephone;

    @NotBlank(message = "Le mobile  ne peut pas être vide")
    @NotNull(message = "Le mobile ne peut pas être null")
    private String mobile;

    @NotBlank(message = "L'email ne peut pas être vide")
    @NotNull(message = "L'email ne peut pas être null")
    private String email;

    @NotBlank(message = "L'email de UBO ne peut pas être vide")
    @NotNull(message = "L'email de UBO ne peut pas être null")
    private String emailUbo;

    @NotBlank(message = "L'adresse ne peut pas être vide")
    @NotNull(message = "L'adresse ne peut pas être null")
    private String adresse;

    @NotBlank(message = "Le codePostal ne peut pas être vide")
    @NotNull(message = "Le codePostal ne peut pas être null")
    private String codePostal;

    @NotBlank(message = "La ville ne peut pas être vide")
    @NotNull(message = "La ville ne peut pas être null")
    private String ville;

    @NotBlank(message = "Le pays d'origine ne peut pas être vide")
    @NotNull(message = "Le pays d'origine ne peut pas être null")
    private String paysOrigine;

    @NotBlank(message = "L'université d'origine ne peut pas être vide")
    @NotNull(message = "L'université d'origine ne peut pas être null")
    private String universiteOrigine;

    @NotNull(message = "Le groupe TP ne peut pas être null")
    private Long groupeTp;

    @NotNull(message = "Le groupe Anglais ne peut pas être null")
    private Long groupeAnglais;

    @JsonProperty("CodeFormation")
    private String CodeFormation;

    @NotBlank(message = "L'année universitaire ne peut pas être vide")
    @NotNull(message = "L'année universitaire ne peut pas être null")
    private String anneeUniversitaire;
}