package fr.ubo.dosi.projectagile.cscievaebackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Table(name = "QUESTION")
public class Question {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "QUE_SEQ"
    )
    @SequenceGenerator(
            name = "QUE_SEQ",
            sequenceName = "QUE_SEQ",
            allocationSize = 1
    )
    @Column(name = "ID_QUESTION", nullable = false)
    private Integer id;

    @Size(max = 10)
    @NotNull
    @Column(name = "TYPE", nullable = false, length = 10)
    private String type;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "NO_ENSEIGNANT")
    private Enseignant noEnseignant;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "ID_QUALIFICATIF", nullable = false)
    private Qualificatif idQualificatif;

    @Size(max = 64)
    @NotNull
    @Column(name = "INTITULE", nullable = false, length = 64)
    private String intitule;

    @OneToMany(mappedBy = "idQuestion")
    private Set<QuestionEvaluation> questionEvaluations = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idQuestion")
    private Set<RubriqueQuestion> rubriqueQuestions = new LinkedHashSet<>();

}