package fr.ubo.dosi.projectagile.cscievaebackend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "QUESTION")
public class Question {
    @Id
    @Column(name = "ID_QUESTION", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 10)
    @NotNull(message = "Le type de question ne peut pas être null")
    @NotEmpty(message = "Le type de question ne peut pas être vide")
    @Column(name = "TYPE", nullable = false, length = 10)
    private String type;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "NO_ENSEIGNANT")
    @JsonBackReference
    private Enseignant noEnseignant;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "ID_QUALIFICATIF", nullable = false)
    private Qualificatif idQualificatif;

    @Size(max = 64)
    @NotNull(message = "L'intitulé ne peut pas être null")
    @NotEmpty(message = "L'intitulé ne peut pas être vide")
    @Column(name = "INTITULE", nullable = false, length = 64)
    private String intitule;
    @JsonIgnore
    @OneToMany(mappedBy = "idQuestion")
    private Set<QuestionEvaluation> questionEvaluations = new LinkedHashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "idQuestion")
    private Set<RubriqueQuestion> rubriqueQuestions = new LinkedHashSet<>();

}