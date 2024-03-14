package fr.ubo.dosi.projectagile.cscievaebackend.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
public class EvaluationSaveDTO {

    private  Long id;
    @NotBlank(message = "Le code de la formation ne peut pas être vide")
    @NotNull(message = "Le code de la formation ne peut pas être null")
    @NotEmpty(message = "Le code de la formation ne peut pas être vide")
    private String codeFormation;

    private String periode;

    @NotBlank(message = "L'année pro ne peut pas être vide")
    @NotNull(message = " L'année pro ne peut pas être null")
    @NotEmpty(message = "L'année pro ne peut pas être vide")
    private String anneePro;

    @NotBlank(message = " Le code de Unité d'enseignement ne peut pas être vide")
    @NotNull(message = "Le code de Unité d'enseignement ne peut pas être null")
    @NotEmpty(message = "Le code de Unité d'enseignement ne peut pas être vide")
    private String codeUE;

    private String codeEC;

    @NotBlank(message = "La désignation de la formation ne peut pas être vide")
    @NotNull(message = "La désignation de la formation ne peut pas être null")
    @NotEmpty(message = "La désignation de la formation ne peut pas être vide")
    private String designation;

    private LocalDate debutReponse;

    private LocalDate finReponse;

    @JsonProperty("RubriqueQuestion")
    private List<IncomingRubriqueQuestionDTO> RubriqueQuestion;

}