package it.unimol.vino.models.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.*;

@Data
@NoArgsConstructor(force = true)
@Builder
@AllArgsConstructor
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
    @JoinColumn(name = "aborted_by")
    private User userWhoAborted;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "abortion_date")
    private Date abortionDate;

    @Column(name = "abortion_description")
    private String abortionDescription;

    @OneToMany(mappedBy = "process")
    private List<UserModifyProcess> modifiers;

    @OneToMany(mappedBy = "process")
    private List<UserProgressesProcess> userProgressProcessList;

    @OneToMany(
            mappedBy = "process",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @NonNull
    List<ProcessHasStates> states;

    @ManyToOne
    private ProcessHasStates currentState;

    @OneToMany(
            mappedBy = "process",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @NonNull
    List<ProcessUseItem> item;

    @OneToMany(
            mappedBy = "process",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @NonNull
    List<ProcessUseContribution> contribution;


    @NonNull
    @Min(value = 0, message = "Wine waste must be greater than 0")
    @Column(nullable = false)
    private Integer wineWaste;

    @NonNull
    @Min(value = 0, message = "stalk waste must be greater than 0")
    @Column(nullable = false)
    private Integer stalkWaste;

    @NonNull
    @Min(value = 0, message = "current waste must be greater than 0")
    @Column(nullable = false)
    private Integer currentWaste;


    public Process(@NotEmpty List<State> states,
                   @NotEmpty Map<Item, Integer> itemQuantityMap,
                   @NotEmpty Map<Contribution, Double> contributionQuantityMap
    ) {
        this.currentWaste = 0;
        this.stalkWaste = 0;
        this.wineWaste = 0;
        this.creationDate = new Date();

        if (Objects.isNull(this.states))
            this.states = new ArrayList<>();


        states.forEach(state -> this.addState(state, states.indexOf(state)));
        this.currentState = this.states.get(0);
        this.currentState.setStartDate(new Date());

        if (Objects.isNull(this.item))
            this.item = new ArrayList<>();

        itemQuantityMap.forEach(this::addItem);

        if (Objects.isNull(this.contribution))
            this.contribution = new ArrayList<>();

        contributionQuantityMap.forEach(this::addContribution);
    }

    public void addState(State state, Integer sequence) {
        ProcessHasStates processHasStates = ProcessHasStates.builder()
                .process(this)
                .state(state)
                .sequence(sequence)
                .build();

        this.states.add(processHasStates);
    }

    public Optional<ProcessHasStates> getNextState() {
        List<ProcessHasStates> sortedStates = this.getStatesOrderedBySequence();
        ProcessHasStates currentState = this.getCurrentState();

        return sortedStates.stream().filter(state -> state.getSequence().equals(currentState.getSequence() + 1))
                .findFirst();
    }

    public List<ProcessHasStates> getStatesOrderedBySequence() {
        return this.states.stream()
                .sorted(Comparator.comparing(ProcessHasStates::getSequence))
                .toList();
    }


    public void addItem(Item item, Integer usedQuantity) {
        ProcessUseItem processUseItem = ProcessUseItem.builder()
                .item(item)
                .process(this)
                .usedQuantity(usedQuantity)
                .build();

        this.item.add(processUseItem);
    }

    public void addContribution(Contribution contribution, Double quantity) {
        ProcessUseContribution processUseContribution = ProcessUseContribution.builder()
                .contribution(contribution)
                .process(this)
                .quantity(quantity)
                .build();

        this.contribution.add(processUseContribution);
    }

    public User getUserWhoProgressedToTheCurrentState() {
        List<User> users = this.userProgressProcessList.stream()
                .filter(userProgressesProcess -> userProgressesProcess.getProcess().getId().equals(this.id))
                .map(UserProgressesProcess::getUser)
                .toList();
        return users.stream().findFirst().orElse(this.creator);
    }
}
