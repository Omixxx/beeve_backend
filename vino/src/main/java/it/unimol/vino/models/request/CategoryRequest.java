package it.unimol.vino.models.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;
@Getter
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class CategoryRequest {

    @NonNull
    private String name;

}
