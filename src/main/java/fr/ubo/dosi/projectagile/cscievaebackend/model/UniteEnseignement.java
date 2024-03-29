package fr.ubo.dosi.projectagile.cscievaebackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "UNITE_ENSEIGNEMENT")
public class UniteEnseignement {
    @EmbeddedId
    private UniteEnseignementId id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "CODE_FORMATION", nullable = false,insertable=false, updatable=false)
    private Formation formation;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "NO_ENSEIGNANT", nullable = false)
    @ToString.Exclude
    private Enseignant noEnseignant;

    @Size(max = 64)
    @NotNull
    @OrderColumn(name = "DESIGNATION")
    @Column(name = "DESIGNATION", nullable = false, length = 64)
    private String designation;

    @Size(max = 3)
    @NotNull
    @Column(name = "SEMESTRE", nullable = false, length = 3,columnDefinition = "CHAR(3)")
    private String semestre;

    @Size(max = 256)
    @Column(name = "DESCRIPTION", length = 256)
    private String description;

    @Column(name = "NBH_CM")
    private Long nbhCm;

    @Column(name = "NBH_TD")
    private Short nbhTd;

    @Column(name = "NBH_TP")
    private Short nbhTp;

    @OneToMany(mappedBy = "uniteEnseignement")
    @OrderColumn(name = "ID_EVALUATION")
    @JsonIgnore
    private Set<Evaluation> evaluations = new LinkedHashSet<>();
    @Transient
    private Double totaleHeures = 0.0;

    public void setTotalHeures() {
        totaleHeures = getNbhCm() * 1.5 + getNbhTd() + getNbhTp();
    }
}