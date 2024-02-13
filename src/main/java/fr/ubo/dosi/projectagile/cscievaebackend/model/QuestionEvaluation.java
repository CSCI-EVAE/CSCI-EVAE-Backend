package fr.ubo.dosi.projectagile.cscievaebackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "question_evaluation" )
public class QuestionEvaluation {
    @Id
    @Column(name = "id_question_evaluation", nullable = false)
    private Long id;

    @Size(max = 64)
    @Column(name = "intitule", length = 64)
    private String intitule;

    @NotNull
    @Column(name = "ordre", nullable = false)
    private Byte ordre;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_qualificatif")
    private Qualificatif idQualificatif;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_question")
    private Question idQuestion;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_rubrique_evaluation", nullable = false)
    private RubriqueEvaluation idRubriqueEvaluation;

}