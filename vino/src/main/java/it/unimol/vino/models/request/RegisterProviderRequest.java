package it.unimol.vino.models.request;


import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
public class RegisterProviderRequest {

    @NonNull()
    @NotBlank(message = "name must be not blank")
    private String name;

    @NonNull()
    @NotBlank(message = "phone number must be not blank")
    private String phone_number;

    @NonNull()
    @Email(message = "email must be valid")
    @NotBlank(message = "email must be not blank")
    private String email;

    /*
    @NonNull()
    @NotBlank(message = "address must be not blank")
    private String address;

    @NonNull()
    @NotBlank(message = "website url must be not blank")
    private String website_url;

     */


}
