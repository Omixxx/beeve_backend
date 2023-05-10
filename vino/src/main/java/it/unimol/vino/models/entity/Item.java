package it.unimol.vino.models.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Builder
@Entity(name = "item")
@Table(name = "item")

public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "capacity")
    private Long capacity;

    @Column(name = "description")
    private String description;

    @Column(name = "total_quantity")
    private Integer totQuantity;

    @OneToMany(mappedBy = "item", orphanRemoval = true, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ProviderSupplyItem> providerSupplyItemList;

    @ManyToOne
    @JsonIgnore
    private Category category;


    public void addProviderMapping(@NonNull Provider provider, @NonNull Integer quantity, @NonNull Date date) {

        ProviderSupplyItem providerSupplyItem =ProviderSupplyItem.builder()
                .quantity(quantity)
                .date(date)
                .provider(provider)
                .item(this)
                .build();


        this.providerSupplyItemList.add(providerSupplyItem);

    }
    public void addQuantity(Integer quantity){
        this.totQuantity+=quantity;
    }

}

