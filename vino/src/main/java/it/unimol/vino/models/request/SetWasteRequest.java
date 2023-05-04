package it.unimol.vino.models.request;

import lombok.*;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
public class SetWasteRequest {

    @NonNull
    private Long waste;
    @NonNull
    private Long processId;

    @NonNull
    private Long stateId;

}
