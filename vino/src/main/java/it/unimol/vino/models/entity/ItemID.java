package it.unimol.vino.models.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class ItemID implements Serializable {
    private Long capacity;
    private String name;
    private Category category;
}
