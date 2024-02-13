package fr.ubo.dosi.projectagile.cscievaebackend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "reponse_question" )
public class ReponseQuestion {
    @EmbeddedId
    private ReponseQuestionId id;

    @MapsId("idQuestionEvaluation")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "ID_QUESTION_EVALUATION", nullable = false)
    private QuestionEvaluation idQuestionEvaluation;

    @MapsId("idReponseQuestion")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "ID_REPONSE_QUESTION", nullable = false)
    private ReponseEvaluation idReponseQuestion;

    @Column(name = "positionnement", precision = 38)
    private BigDecimal positionnement;

}