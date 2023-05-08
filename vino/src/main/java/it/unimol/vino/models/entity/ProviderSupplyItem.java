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
@Data
@Builder
@Table
public class ProviderSupplyItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;


    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "date")
    private Date date;


    @ManyToOne(fetch = FetchType.EAGER)
    private Provider provider;


    @ManyToOne(fetch = FetchType.LAZY)
    private Item item;

}
