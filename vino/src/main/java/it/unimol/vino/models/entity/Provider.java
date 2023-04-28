package it.unimol.vino.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.unimol.vino.models.response.ItemsProvidedByProvider;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity(name="provider")
@Table(name="provider")

@NamedNativeQuery(name ="Provider.findProvidedItemsById",
                                            query = "SELECT item.id as iid,item.capacity as cap,item.description as descr,provider_supply_item.quantity as qua,provider_supply_item.date as dat FROM item,provider_supply_item WHERE provider_supply_item.provider_id= :id AND provider_supply_item.item_id=item.id",
                                            resultSetMapping = "Mapping.ItemsProvidedByProvider")
@SqlResultSetMapping(name="Mapping.ItemsProvidedByProvider",
                    classes = @ConstructorResult(targetClass = ItemsProvidedByProvider.class,
                                                    columns = {@ColumnResult(name="iid"),
                                                                @ColumnResult(name="cap"),
                                                                @ColumnResult(name = "descr"),
                                                                @ColumnResult(name="qua"),
                                                                @ColumnResult(name="dat")}))
public class Provider implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "phone_number")
    private String phone_number;

    @NonNull
    @Column(unique = true)
    private String email;

    @Column(name = "address")
    private String address;

    @Column(name="website_url")
    private String website_url;

    @OneToMany(mappedBy = "provider",orphanRemoval = true, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ProviderSupplyItem> providerSupplyItemList;

}
