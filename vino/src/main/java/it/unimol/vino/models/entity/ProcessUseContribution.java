package it.unimol.vino.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.io.Serializable;

@Data
@Entity
@Getter
@Setter
@NoArgsConstructor(force = true)
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@IdClass(ProcessUseContributionId.class)
public class ProcessUseContribution implements Serializable {
    @Id
    @ManyToOne
    private Process process;

    @Id
    @ManyToOne
    private Contribution contribution;

    @Column(name = "quantity")
    @Positive
    @NonNull
    private Double quantity;

}
