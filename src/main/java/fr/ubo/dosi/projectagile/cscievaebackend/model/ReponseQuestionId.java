package fr.ubo.dosi.projectagile.cscievaebackend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class ReponseQuestionId implements Serializable {
    private static final long serialVersionUID = 37045820100946828L;
    @Column(name = "ID_QUESTION_EVALUATION", nullable = false)
    private Long idQuestionEvaluation;

    @Column(name = "ID_REPONSE_EVALUATION", nullable = false)
    private Long idReponseEvaluation;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ReponseQuestionId entity = (ReponseQuestionId) o;
        return Objects.equals(this.idQuestionEvaluation, entity.idQuestionEvaluation) &&
                Objects.equals(this.idReponseEvaluation, entity.idReponseEvaluation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idQuestionEvaluation, idReponseEvaluation);
    }

}