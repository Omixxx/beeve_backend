package it.unimol.vino.models.request;

import it.unimol.vino.models.entity.UserSectorPermission;
import it.unimol.vino.models.enums.SectorName;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdatePermissionsRequest {

    @NotNull
    private HashMap<SectorName, UserSectorPermission> permissions;
}
