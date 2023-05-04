package it.unimol.vino.models.entity;;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "category")
@Table(name = "category")
public class Category {

    @Id
    @Column(name = "name", unique = true)
    private String name;
}
