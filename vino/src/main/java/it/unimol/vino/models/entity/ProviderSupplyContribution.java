package it.unimol.vino.models.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor (force = true)
@AllArgsConstructor
@EqualsAndHashCode
@IdClass(ProviderSupplyContributionId.class)
public class ProviderSupplyContribution implements Serializable {

    @Column(name = "date")
    @NonNull
    private String date;

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    private Provider provider;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    private Contribution contribution;

}
