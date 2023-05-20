package it.unimol.vino.models.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "grape_type")
@Table(name = "grape_type", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"species", "color"})
})
@EqualsAndHashCode()
public class GrapeType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Il nome non può essere vuoto")
    @Column(name = "species")
    private String species;

    @NotBlank(message = "Il tipo d'uva deve avere un colore")
    @Column(name = "color")
    private String color;

    @OneToMany(mappedBy = "associatedGrapeType")
    private List<Contribution> contributions;
}
