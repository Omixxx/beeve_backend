package it.unimol.vino.models.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@Builder
@Entity(name = "grape_type")
@Table(name = "grape_type")
@EqualsAndHashCode()
public class GrapeType {
    
    @Id
    @NotBlank(message = "Il nome non può essere vuoto")
    @Column(name = "grape_type")
    private String name;

    @NotBlank(message = "Il tipo d'uva deve avere un colore")
    @Column(name = "grape_color")
    private String grapeColor;

    @NotBlank(message = "Il tipo d'uva deve avere una specie")
    @Column(name = "species")
    private String species;

    //TODO: inserire il mapping con i conferimenti;

}
