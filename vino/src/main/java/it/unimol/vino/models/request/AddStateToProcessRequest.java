package it.unimol.vino.models.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
public class AddStateToProcessRequest {
    @NotNull(message = "L'id dello stato non può essere nullo")
    private Long stateId;

    @NotNull(message = "L'id del processo non può essere nullo")
    private Long processId;

    @NotNull(message = "La sequenza non può essere nulla")
    private Integer sequence;

    private Long waste = null;

    private Date startDate = null;


}
