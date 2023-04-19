package it.unimol.vino.models.request;

import it.unimol.vino.models.entity.UserSectorPermission;
import it.unimol.vino.models.enums.SectorName;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;


@RequiredArgsConstructor
@Data
public class UpdatePermissionsRequest {
    private HashMap<SectorName, UserSectorPermission> permissions;
}
