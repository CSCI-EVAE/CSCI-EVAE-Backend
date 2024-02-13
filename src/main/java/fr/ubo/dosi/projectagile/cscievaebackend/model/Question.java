package fr.ubo.dosi.projectagile.cscievaebackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "question" )
public class Question {
    @Id
    @Column(name = "id_question", nullable = false)
    private Long id;

    @Size(max = 64)
    @NotNull
    @Column(name = "`intitulֹ`", nullable = false, length = 64)
    private String intitulֹ;

    @Size(max = 10)
    @NotNull
    @Column(name = "type", nullable = false, length = 10)
    private String type;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_qualificatif", nullable = false)
    private Qualificatif idQualificatif;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "no_enseignant")
    private Enseignant noEnseignant;

}