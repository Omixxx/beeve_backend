package it.unimol.vino.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.websocket.OnError;
import lombok.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class State {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String wasteUnit;

//    @OneToMany(mappedBy = "state")
//    @JsonIgnore
//    private List<Process> processesNowOpened;

    @OneToMany(mappedBy = "state", cascade = CascadeType.ALL)
    private List<ProcessHasStates> processes;

}
