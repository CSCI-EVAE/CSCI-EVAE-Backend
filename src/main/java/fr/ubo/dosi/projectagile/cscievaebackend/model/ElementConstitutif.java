package fr.ubo.dosi.projectagile.cscievaebackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "element_constitutif" )
public class ElementConstitutif {
    @EmbeddedId
    private ElementConstitutifId id;

    @Size(max = 240)
    @NotNull
    @Column(name = "description", nullable = false, length = 240)
    private String description;

    @Size(max = 64)
    @NotNull
    @Column(name = "designation", nullable = false, length = 64)
    private String designation;

    @Column(name = "nbh_cm")
    private Byte nbhCm;

    @Column(name = "nbh_td")
    private Byte nbhTd;

    @Column(name = "nbh_tp")
    private Byte nbhTp;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "no_enseignant", nullable = false)
    private Enseignant noEnseignant;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "unite_enseignement_code_ue", nullable = false)
    private UniteEnseignement uniteEnseignementCodeUe;

}