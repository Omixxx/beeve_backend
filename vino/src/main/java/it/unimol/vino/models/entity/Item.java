package it.unimol.vino.models.entity;


import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity(name="item")
@Table(name="item")

public class Item implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name="capacity")
    private Long capacity;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "item" , orphanRemoval = true, cascade = CascadeType.ALL)
    private List<ProviderSupplyItem> providerSupplyItemList;


    public void addMapping(@NonNull Provider provider, @NonNull ProviderSupplyItem providerSupplyItem){

        ProviderSupplyItem providerSupplyItem1 = ProviderSupplyItem.builder()
                .quantity(providerSupplyItem.getQuantity())
                .item(this)
                .provider(provider)
                .date(providerSupplyItem.getDate())
                .build();

        if(this.providerSupplyItemList ==null){
            this.providerSupplyItemList=new ArrayList<>();
        }
        this.providerSupplyItemList.add(providerSupplyItem1);
        }


    }

