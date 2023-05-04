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
@Table(name = "grape_type")
@EqualsAndHashCode()
public class GrapeType {
    
    @Id
    @NotBlank(message = "Il nome non può essere vuoto")
    @Column(name = "grape_type")
    private String id;

    @NotBlank(message = "Il tipo d'uva deve avere un colore")
    @Column(name = "color")
    private String color;

    @NotBlank(message = "Il tipo d'uva deve avere una specie")
    @Column(name = "species")
    private String species;

    @OneToMany(mappedBy = "associatedGrapeType")
    private List<Contribution> contributions;
}
