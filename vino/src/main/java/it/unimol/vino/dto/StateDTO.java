package it.unimol.vino.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StateDTO {
    private Long id;
    private String name;
    private Boolean doesProduceWaste;
}
