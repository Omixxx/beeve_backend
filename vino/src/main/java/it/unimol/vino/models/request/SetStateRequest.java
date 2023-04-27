package it.unimol.vino.models.request;

import lombok.*;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
public class SetStateRequest {
    @NonNull()
    private Long stateId;
    @NonNull()
    private Long processId;
}
