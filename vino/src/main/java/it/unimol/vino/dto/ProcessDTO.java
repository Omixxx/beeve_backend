package it.unimol.vino.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import it.unimol.vino.models.entity.Process;
import it.unimol.vino.models.entity.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class ProcessDTO {

    private Long id;

    private User creator;

    private Date creationDate;

    private User canceller;

    private Date cancellationDate;

    private String cancellationDescription;

    private List<UserModifyProcess> modifiers;

    private List<UserProgressesProcess> userProgressProcessList;

    List<ProcessHasStates> states;

    private ProcessHasStates currentState;

    private Integer wineWaste;

    private Integer stalkWaste;

    private Integer currentWaste;


    public static ProcessDTO getFullProcessDTO(Process process) {
        return ProcessDTO.builder()
                .id(process.getId())
                .creator(process.getCreator())
                .creationDate(process.getCreationDate())
                .canceller(process.getCanceller())
                .cancellationDate(process.getCancellationDate())
                .cancellationDescription(process.getCancellationDescription())
                .modifiers(process.getModifiers())
                .userProgressProcessList(process.getUserProgressProcessList())
                .states(process.getStates())
                .currentState(process.getCurrentState())
                .wineWaste(process.getWineWaste())
                .stalkWaste(process.getStalkWaste())
                .currentWaste(process.getCurrentWaste())
                .build();
    }
}
