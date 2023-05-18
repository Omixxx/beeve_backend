package it.unimol.vino.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import it.unimol.vino.models.entity.User;
import it.unimol.vino.models.entity.UserModifyProcess;
import it.unimol.vino.models.entity.UserProgressesProcess;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProcessDTO {

    private Long id;

    private UserDTO creator;

    private Date creationDate;

    private User userWhoAborted;

    private Date abortDate;

    private String abortDescription;

    private List<UserModifyProcess> modifiers;

    private List<UserProgressesProcess> userProgressProcessList;

    private List<StateDTO> states;

    private CurrentStateDTO currentState;

    private Integer wineWaste;

    private Integer stalkWaste;

    private Integer currentWaste;

    private List<ContributionDTO> contributions;
}
