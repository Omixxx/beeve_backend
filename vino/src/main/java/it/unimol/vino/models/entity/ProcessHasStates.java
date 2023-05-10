package it.unimol.vino.models.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor(force = true)
@Builder
@AllArgsConstructor
public class ProcessHasStates {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Process process;

    @ManyToOne
    private State state;

    @NonNull
    private Integer sequence;

    private Date startDate;

    private Date endDate;
}
