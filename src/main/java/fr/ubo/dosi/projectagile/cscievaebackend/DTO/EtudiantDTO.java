package fr.ubo.dosi.projectagile.cscievaebackend.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EtudiantDTO {
    private String noEtudiant;
    private String nom;
    private String prenom;
    private String sexe;
    private LocalDate dateNaissance;
    private String lieuNaissance;
    private String nationalite;
    private String telephone;
    private String mobile;
    private String email;
    private String emailUbo;
    private String adresse;
    private String codePostal;
    private String ville;
    private String paysOrigine;
    private String universiteOrigine;
    private Long groupeTp;
    private Long groupeAnglais;
    @JsonProperty("CodeFormation")
    private String CodeFormation;
    private String anneeUniversitaire;
}