package it.unimol.vino.models.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;
<<<<<<<<< Temporary merge branch 1
@Setter
=========

>>>>>>>>> Temporary merge branch 2
@Getter
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class CategoryRequest {

    @NonNull
    private String name;

    private Boolean isPrimary;

}
