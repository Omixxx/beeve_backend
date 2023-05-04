package it.unimol.vino.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
@Entity
public class State {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @NotBlank(message = "Il nome dello stato non può essere vuoto")
    private String name;

    @NonNull
    private Boolean doesProduceWaste;

    @OneToMany(mappedBy = "state", cascade = CascadeType.ALL)
    private List<ProcessHasStates> processes;

}
