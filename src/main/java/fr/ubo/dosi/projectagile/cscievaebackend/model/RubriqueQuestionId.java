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
public class RubriqueQuestionId implements Serializable {
    private static final long serialVersionUID = -8985528901286615684L;
    @Column(name = "ID_QUESTION", nullable = false)
    private Long idQuestion;

    @Column(name = "ID_RUBRIQUE", nullable = false)
    private Long idRubrique;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        RubriqueQuestionId entity = (RubriqueQuestionId) o;
        return Objects.equals(this.idRubrique, entity.idRubrique) &&
                Objects.equals(this.idQuestion, entity.idQuestion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRubrique, idQuestion);
    }

}