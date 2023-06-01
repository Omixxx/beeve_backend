package it.unimol.vino.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import it.unimol.vino.models.entity.UserModifyProcess;
import it.unimol.vino.models.entity.UserProgressesProcess;
import it.unimol.vino.models.entity.UserSectorPermission;
import it.unimol.vino.models.enums.Role;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;
    private String password;

    private Boolean isAdmin;

    private List<UserProgressesProcess> progressedProcesses;

    private List<UserModifyProcess> modifiedProcesses;

    private List<UserSectorPermission> permissions;

}
