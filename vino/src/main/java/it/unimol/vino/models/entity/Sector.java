package it.unimol.vino.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.unimol.vino.models.enums.SectorName;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Sector implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Column(unique = true)
    @Enumerated(EnumType.STRING)
    private SectorName sectorName;

    @OneToMany(mappedBy = "sector", orphanRemoval = true, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<UserSectorPermission> users;

    public Sector(SectorName sectorName) {
        this.sectorName = sectorName;
    }
}
