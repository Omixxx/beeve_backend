package it.unimol.vino.models.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProcessHasStates implements Serializable {
    @Id
    @ManyToOne
    private Process process;

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    private State state;

    @NonNull
    private Long sequence;

    private Long waste;

    private Date startDate;

    private Date endDate;






}
