package it.unimol.vino.models.request;

import it.unimol.vino.models.entity.State;
import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
public class AddStateToProcessRequest {
    @NonNull
    private Long stateId;

    @NonNull
    private Long processId;

    @NonNull
    private Integer sequence;

    private Long waste = null;

    private Date startDate = null;


}
