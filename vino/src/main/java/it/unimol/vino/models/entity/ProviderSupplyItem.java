package it.unimol.vino.models.entity;


import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@IdClass(ProviderSupplyItemId.class)
public class ProviderSupplyItem implements Serializable {


    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "date")
    private Date date;

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    private Provider provider;


    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    private Item item;

}
