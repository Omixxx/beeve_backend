package it.unimol.vino.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Data
public class SectorPermissionDTO {

    private SectorDTO sector;

    private Boolean canRead;

    private Boolean canWrite;

    private Boolean canDelete;

    private Boolean canUpdate;
}
