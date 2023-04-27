package it.unimol.vino.models.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Process {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date startDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private ProcessHasStates state;

    private String name;

    @OneToMany(mappedBy = "process", cascade = CascadeType.ALL)
    List<ProcessHasStates> states;

    public void addState(State state, Long sequence){
        ProcessHasStates processHasStates = new ProcessHasStates(this, state, sequence, null, null, null);
//        processHasStates.setProcess(this);
//        processHasStates.setState(state);
//        processHasStates.setSequence(sequence);
//        System.out.println(processHasStates.getProcess().id);
        this.states.add(processHasStates);
        System.out.println(this.states.get(0).getProcess().getId());
    }

    public void setState(ProcessHasStates state){
        this.state = state;
    }
    public void setState(State state){
        this.setState(this.getStates().stream().filter(processHasStates ->
                processHasStates.getState().getId().equals(state.getId())).findFirst().get());
    }

    public void setWaste(Long waste){
        this.getState().setWaste(waste);
    }
}
