package fr.ubo.dosi.projectagile.cscievaebackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "unite_enseignement" )
public class UniteEnseignement {
    @Id
    @Size(max = 8)
    @Column(name = "code_ue", nullable = false, length = 8)
    private String codeUe;

    @Size(max = 256)
    @Column(name = "description", length = 256)
    private String description;

    @Size(max = 64)
    @NotNull
    @Column(name = "designation", nullable = false, length = 64)
    private String designation;

    @Column(name = "nbh_cm", precision = 38)
    private BigDecimal nbhCm;

    @Column(name = "nbh_td")
    private Byte nbhTd;

    @Column(name = "nbh_tp")
    private Byte nbhTp;

    @Size(max = 3)
    @NotNull
    @Column(name = "semestre", nullable = false, length = 3)
    private String semestre;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "code_formation", nullable = false)
    private Formation codeFormation;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "no_enseignant", nullable = false)
    private Enseignant noEnseignant;

}