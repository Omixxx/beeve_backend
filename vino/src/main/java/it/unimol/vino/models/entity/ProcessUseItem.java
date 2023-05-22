package it.unimol.vino.models.entity;

import jakarta.persistence.*;
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
@IdClass(ProcessUseItemId.class)
public class ProcessUseItem implements Serializable {
    @Id
    @ManyToOne
    private Process process;

    @Id
    @ManyToOne
    private Item item;

    @Column(name = "used_quantity")
    @NonNull
    private Integer usedQuantity;
}
