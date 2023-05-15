package it.unimol.vino.models.entity;

import it.unimol.vino.models.enums.SectorName;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Sector {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @Enumerated(EnumType.STRING)
    private SectorName sectorName;

    @OneToMany(mappedBy = "sector", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<UserSectorPermission> users;

    public Sector(SectorName sectorName) {
        this.sectorName = sectorName;
    }
}
