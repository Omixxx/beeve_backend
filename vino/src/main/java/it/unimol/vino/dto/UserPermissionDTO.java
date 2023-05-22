package it.unimol.vino.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import it.unimol.vino.models.entity.Sector;
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
