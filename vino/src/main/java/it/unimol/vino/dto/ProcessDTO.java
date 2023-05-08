package it.unimol.vino.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import it.unimol.vino.models.entity.Process;
import it.unimol.vino.models.entity.*;
import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProcessDTO {

    private Long id;

    private UserDTO creator;

    private Date creationDate;

    private User canceller;

    private Date cancellationDate;

    private String cancellationDescription;

    private List<UserModifyProcess> modifiers;

    private List<UserProgressesProcess> userProgressProcessList;

    private List<StateDTO> states;

    private StateDTO currentState;

    private Integer wineWaste;

    private Integer stalkWaste;

    private Integer currentWaste;

    public static ProcessDTO getFullProcessDTO(@NotNull Process process) {
        User user = process.getCreator();
        ProcessHasStates processHasState = process.getCurrentState();


        return ProcessDTO.builder()
                .id(process.getId())
                .creator(UserDTO.builder()
                        .email(user.getEmail())
                        .role(user.getRole())
                        .build()
                )
                .states(process.getStates().stream()
                        .map(ProcessHasStates::getState)
                        .map(state -> StateDTO.builder()
                                .id(state.getId())
                                .name(state.getName())
                                .doesProduceWaste(state.getDoesProduceWaste())
                                .build()).toList()
                )
                .currentState(Objects.isNull(processHasState) ? null : StateDTO.builder()
                        .id(processHasState.getState().getId())
                        .name(processHasState.getState().getName())
                        .doesProduceWaste(processHasState.getState().getDoesProduceWaste())
                        .build()
                )
                .wineWaste(process.getWineWaste())
                .stalkWaste(process.getStalkWaste())
                .currentWaste(process.getCurrentWaste())
                .build();
    }
}
