package fr.ubo.dosi.projectagile.cscievaebackend.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EvaluationSaveDTO {
    @NotBlank(message = "Le code de la formation ne peut pas être vide")
    @NotNull(message = "Le code de la formation ne peut pas être null")
    @NotEmpty(message = "Le code de la formation ne peut pas être vide")
    private String codeFormation;
    @NotBlank(message = "La période ne peut pas être vide")
    @NotNull(message = "La période ne peut pas être  null")
    @NotEmpty(message = "La période ne peut pas être vide")
    private String periode;
    @NotBlank(message = "L'année pro ne peut pas être vide")
    @NotNull(message = " L'année pro ne peut pas être null")
    @NotEmpty(message = "L'année pro ne peut pas être vide")
    private String anneePro;
    @NotBlank(message = " Le code de Unité d'enseignement ne peut pas être vide")
    @NotNull(message = "Le code de Unité d'enseignement ne peut pas être null")
    @NotEmpty(message = "Le code de Unité d'enseignement ne peut pas être vide")
    private String codeUE;
    @NotBlank(message = "Le code de element constitutif ne peut pas être vide")
    @NotNull(message = "Le code de element constitutif ne peut pas être null")
    @NotEmpty(message = "Le code de element constitutif ne peut pas être vide")
    private String codeEC;
    @NotBlank(message = "La désignation de la formation ne peut pas être vide")
    @NotNull(message = "La désignation de la formation ne peut pas être null")
    @NotEmpty(message = "La désignation de la formation ne peut pas être vide")
    private String designation;
    @NotBlank(message = "Le debut de reponse ne peut pas être vide")
    @NotNull(message = "Le debut de reponse ne peut pas être null")
    @NotEmpty(message = "Le debut de reponse ne peut pas être vide")
    private LocalDate debutReponse;
    @NotBlank(message = "Le fin de reponse ne peut pas être vide")
    @NotNull(message = "Le fin de reponse ne peut pas être null")
    @NotEmpty(message = "Le fin de reponse ne peut pas être vide")
    private LocalDate finReponse;
    private List<IncomingRubriqueQuestionDTO> RubriqueQuestion;
}