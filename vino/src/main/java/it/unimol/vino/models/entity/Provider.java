package it.unimol.vino.models.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Builder
@Entity(name="provider")
@Table(name="provider")
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
    private List<ProviderSupplyItem> providerSupplyItemList;

}
