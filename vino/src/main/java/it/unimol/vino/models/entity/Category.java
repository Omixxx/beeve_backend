package it.unimol.vino.models.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;


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

    @OneToMany
    private List<Item> itemList;

    @Column(nullable = false)
    private Boolean isPrimary;

    public void addItem(Item item) {
        this.itemList.add(item);
    }
}
