package it.unimol.vino.models.entity;


import it.unimol.vino.exceptions.StateNotFoundException;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
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
    @Column(name = "created_by", nullable = false, updatable = false)
    private User creator;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date creationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "deleted_by")
    private User deleter;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "deletion_date")
    private Date deletionDate;

    @Column(name = "deletion_description")
    private String deletionDescription;

    @OneToMany
    private List<UserModifyProcess> modifiers;


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
        List<ProcessHasStates> sortedStates = this.getStates();
        ProcessHasStates currentState = this.getCurrentState().orElseThrow(
                () -> new StateNotFoundException("Il processo non possiede uno stato corrente")
        );

        return sortedStates.stream().filter(state -> state.getSequence().equals(currentState.getSequence() + 1))
                .findFirst().orElseThrow(() -> new StateNotFoundException("Non ci sono stati successivi"));
    }

    public List<ProcessHasStates> getStates() {
        return this.states.stream()
                .sorted(Comparator.comparing(ProcessHasStates::getSequence))
                .toList();
    }

    public Optional<ProcessHasStates> getCurrentState() {
        return this.states
                .stream()
                .filter(state -> Objects.nonNull(state.getStartDate()) &&
                        Objects.isNull(state.getEndDate()))
                .findFirst();
    }
}
