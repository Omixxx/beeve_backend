package it.unimol.vino.models.entity;


import it.unimol.vino.exceptions.StateNotFoundException;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.*;

@Data
@NoArgsConstructor(force = true)
@Entity
public class Process {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false, updatable = false)
    private User creator;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date creationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cancelled_by")
    private User canceller;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "cancelled_at")
    private Date cancellationDate;

    @Column(name = "cancelled_description")
    private String cancellationDescription;

    @OneToMany(mappedBy = "process")
    private List<UserModifyProcess> modifiers;

    @OneToMany(mappedBy = "process")
    private List<UserProgressesProcess> enablers;

    @OneToMany(
            mappedBy = "process",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @NonNull
    List<ProcessHasStates> states;


    @ManyToOne(fetch = FetchType.EAGER)
    private ProcessHasStates currentState;


    @NonNull
    @Min(value = 0, message = "Wine waste must be greater than 0")
    @Column(nullable = false)
    private Integer wineWaste;

    @NonNull
    @Min(value = 0, message = "Wine waste must be greater than 0")
    @Column(nullable = false)
    private Integer stalkWaste;

    @NonNull
    @Min(value = 0, message = "Wine waste must be greater than 0")
    @Column(nullable = false)
    private Integer currentWaste;


    public Process(@NotEmpty Map<State, Integer> stateSequenceMap) {
        this.currentWaste = 0;
        this.stalkWaste = 0;
        this.wineWaste = 0;
        this.creationDate = new Date();

        if (Objects.isNull(this.states))
            this.states = new ArrayList<>();

        stateSequenceMap.forEach(this::addState);
    }

    public void addState(State state, Integer sequence) {
        ProcessHasStates processHasStates = ProcessHasStates.builder()
                .process(this)
                .state(state)
                .sequence(sequence)
                .build();

        this.states.add(processHasStates);
    }

    public ProcessHasStates getNextState() {
        List<ProcessHasStates> sortedStates = this.getStatesOrderedBySequence();
        ProcessHasStates currentState = this.getCurrentState();

        return sortedStates.stream().filter(state -> state.getSequence().equals(currentState.getSequence() + 1))
                .findFirst().orElseThrow(() -> new StateNotFoundException("Non ci sono stati successivi"));
    }

    public List<ProcessHasStates> getStatesOrderedBySequence() {
        return this.states.stream()
                .sorted(Comparator.comparing(ProcessHasStates::getSequence))
                .toList();
    }
}
