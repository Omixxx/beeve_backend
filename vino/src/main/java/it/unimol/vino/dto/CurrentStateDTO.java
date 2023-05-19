package it.unimol.vino.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CurrentStateDTO {
    private UserDTO user;
    private StateDTO state;
}
