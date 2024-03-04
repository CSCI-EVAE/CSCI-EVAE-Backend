package fr.ubo.dosi.projectagile.cscievaebackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.EmbeddableInstantiator;


@Getter
@Setter
@Entity
@Table(name = "QUALIFICATIF")
public class Qualificatif {
    @Id
    @Column(name = "ID_QUALIFICATIF", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 16)
    @NotNull(message = "Le champ maximal ne doit pas être null")
    @NotEmpty(message = "Le champ maximal ne doit pas être vide")
    @NotBlank(message = "Le champ maximal ne doit pas être vide")
    @Column(name = "MAXIMAL", nullable = false, length = 16)
    private String maximal;

    @Size(max = 16)
    @NotNull(message = "Le champ minimal ne doit pas être  null")
    @NotEmpty(message = "Le champ minimal ne doit pas être vide")
    @NotBlank(message = "Le champ minimal ne doit pas être vide")
    @Column(name = "MINIMAL", nullable = false, length = 16)
    private String minimal;

}