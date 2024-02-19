package fr.ubo.dosi.projectagile.cscievaebackend.model;

import jakarta.persistence.*;
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
@Table(name = "RUBRIQUE_EVALUATION")
public class RubriqueEvaluation {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "REV_SEQ"
    )
    @SequenceGenerator(
            name = "REV_SEQ",
            sequenceName = "REV_SEQ",
            allocationSize = 1
    )
    @Column(name = "ID_RUBRIQUE_EVALUATION", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "ID_EVALUATION", nullable = false)
    private Evaluation idEvaluation;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "ID_RUBRIQUE")
    private Rubrique idRubrique;

    @NotNull
    @Column(name = "ORDRE", nullable = false)
    private Short ordre;

    @Size(max = 64)
    @Column(name = "DESIGNATION", length = 64)
    private String designation;

    @OneToMany(mappedBy = "idRubriqueEvaluation",fetch = FetchType.LAZY)
    private Set<QuestionEvaluation> questionEvaluations = new LinkedHashSet<>();

}