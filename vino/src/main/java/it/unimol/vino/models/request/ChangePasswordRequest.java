package it.unimol.vino.models.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Getter
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class ChangePasswordRequest {

    @NonNull
    private String oldPassword;

    @NonNull
    private String newPassword;
}
