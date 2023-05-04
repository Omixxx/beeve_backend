package it.unimol.vino.models.entity;;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Builder
@Entity(name = "category")
@Table(name = "category")
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @Column(name = "name", unique = true)
    private String name;

    @OneToMany
    private List<Item> itemList;

    public void addItem(Item item){
        this.itemList.add(item);
    }

}
