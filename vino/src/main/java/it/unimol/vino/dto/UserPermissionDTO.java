package it.unimol.vino.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Data
public class UserPermissionDTO {

    private UserDTO user;

    private List<SectorPermissionDTO> permissions;

}
