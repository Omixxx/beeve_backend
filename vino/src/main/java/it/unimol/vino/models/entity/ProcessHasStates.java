package it.unimol.vino.models.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
    @ManyToOne
    private Process process;

    @Id
    @ManyToOne
    private State state;

    @NonNull
    private Integer sequence;

    private Date startDate;

    private Date endDate;
}
