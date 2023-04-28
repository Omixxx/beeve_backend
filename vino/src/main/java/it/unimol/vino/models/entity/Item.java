package it.unimol.vino.models.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

import java.util.Date;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity(name="item")
@Table(name="item")

public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name="capacity")
    private Long capacity;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "item" , orphanRemoval = true, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ProviderSupplyItem> providerSupplyItemList;


    public void addMapping(@NonNull Provider provider, @NonNull Long quantity , @NonNull Date date){

        ProviderSupplyItem providerSupplyItem = new ProviderSupplyItem(
                quantity,
                date,
                provider,
                this);


        this.providerSupplyItemList.add(providerSupplyItem);


        }


    }

