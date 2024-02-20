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
@Table(name = "RUBRIQUE")
public class Rubrique {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "RUB_SEQ"
    )
    @SequenceGenerator(
            name = "RUB_SEQ",
            sequenceName = "RUB_SEQ",
            allocationSize = 1
    )
    @Column(name = "ID_RUBRIQUE", nullable = false)
    private Integer id;

    @Size(max = 10)
    @NotNull
    @Column(name = "TYPE", nullable = false, length = 10)
    private String type;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "NO_ENSEIGNANT")
    private Enseignant noEnseignant;

    @Size(max = 32)
    @NotNull
    @Column(name = "DESIGNATION", nullable = false, length = 32)
    private String designation;

    @Column(name = "ORDRE")
    private Long ordre;

    @OneToMany(mappedBy = "idRubrique")
    private Set<RubriqueQuestion> rubriqueQuestions = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idRubrique")
    private Set<RubriqueEvaluation> rubriqueEvaluations = new LinkedHashSet<>();

}