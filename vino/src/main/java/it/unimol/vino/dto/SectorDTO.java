package it.unimol.vino.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import it.unimol.vino.models.enums.SectorName;
import lombok.Builder;
import lombok.Data;

import java.util.List;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Data
public class SectorDTO {

    private Long id;

    private SectorName sectorName;

    private List<UserDTO> users;

}
