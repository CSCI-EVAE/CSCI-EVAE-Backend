package fr.ubo.dosi.projectagile.cscievaebackend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import fr.ubo.dosi.projectagile.cscievaebackend.DTO.RubriqueQuestionDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Table(name = "RUBRIQUE")

public class Rubrique {
    @Id
    @Column(name = "ID_RUBRIQUE", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 10)
    @NotNull(message = "Le type de question ne peut pas être null")
    @NotEmpty(message = "Le type de question ne peut pas être vide")
    @NotBlank(message = "Le type de question ne peut pas être vide")
    @Column(name = "TYPE", nullable = false, length = 10)
    private String type;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "NO_ENSEIGNANT")
    @JsonBackReference
    private Enseignant noEnseignant;

    @Size(max = 32)
    @NotNull(message = "La désignation ne peut pas être null")
    @NotEmpty(message = "La désignation ne peut pas être vide")
    @Size(min = 1, max = 32, message = "La désignation doit être entre 1 et 32 caractères")
    @NotBlank(message = "La désignation ne peut pas être vide")
    @Column(name = "DESIGNATION", nullable = false, length = 32)
    private String designation;

    @Column(name = "ORDRE")
    private Long ordre;

    @OneToMany(mappedBy = "idRubrique")
    @OrderColumn(name = "ORDRE")
    private Set<RubriqueQuestion> rubriqueQuestions = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idRubrique")
    private Set<RubriqueEvaluation> rubriqueEvaluations = new LinkedHashSet<>();

}