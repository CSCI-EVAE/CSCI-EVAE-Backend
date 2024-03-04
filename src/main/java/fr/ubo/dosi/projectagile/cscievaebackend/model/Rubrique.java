package fr.ubo.dosi.projectagile.cscievaebackend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "RUBRIQUE")
public class Rubrique {
    @Id
    @Column(name = "ID_RUBRIQUE", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 10)
    @NotNull(message = "Type cannot be null")
    @NotEmpty(message = "Type cannot be empty")
    @NotBlank(message = "Type cannot be blank")
    @Column(name = "TYPE", nullable = false, length = 10)
    private String type;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "NO_ENSEIGNANT")
    @JsonBackReference
    private Enseignant noEnseignant;

    @Size(max = 32)
    @NotNull(message = "Designation cannot be null")
    @NotEmpty(message = "Designation cannot be empty")
    @Size(min = 1, max = 32, message = "Designation must be between 1 and 32 characters")
    @NotBlank(message = "Designation cannot be blank")
    @Column(name = "DESIGNATION", nullable = false, length = 32)
    private String designation;

    @Column(name = "ORDRE")
    private Long ordre;

}