package it.unimol.vino.models.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@Entity(name = "category")
@Table(name = "category")
public class Category {

    @Id
    @Column(name = "Category",unique = true)
    @NotBlank(message = "il nome non può essere vuoto")
    private String category;
}
