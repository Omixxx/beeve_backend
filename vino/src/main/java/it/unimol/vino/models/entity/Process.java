package it.unimol.vino.models.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.CreatedDate;

import java.util.*;

@Data
@Getter
@Setter
@NoArgsConstructor(force = true)
@Entity
public class Process {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date creationDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @NonNull
    private ProcessHasStates currentState;

    @OneToMany(mappedBy = "process", cascade = CascadeType.ALL)
    @NonNull
    List<ProcessHasStates> states;

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

    public void setCurrentState(@NotNull ProcessHasStates state) {
        this.currentState = state;
    }


    public void setNextState() {
        this.setCurrentState(this.getNextState());
    }

    //todo: handle exception if there is no next state
    public ProcessHasStates getNextState() {
        List<ProcessHasStates> sortedStates = this.states.stream()
                .sorted(Comparator.comparing(ProcessHasStates::getSequence))
                .toList();
        return sortedStates.get(sortedStates.indexOf(this.currentState) + 1);
    }
}
