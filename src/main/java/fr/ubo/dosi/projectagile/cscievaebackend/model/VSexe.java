package fr.ubo.dosi.projectagile.cscievaebackend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

/**
 * Mapping for DB view
 */
@Getter
@Setter
@Entity
@Immutable
@Table(name = "V_SEXE")
public class VSexe {
    @Id
    @Size(max = 240)
    @NotNull
    @Column(name = "CODE", nullable = false, length = 240)
    private String code;

    @Size(max = 240)
    @Column(name = "ABREVIATION", length = 240)
    private String abreviation;

    @Size(max = 240)
    @Column(name = "SIGNIFICATION", length = 240)
    private String signification;

}