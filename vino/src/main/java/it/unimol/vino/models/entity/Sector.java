package it.unimol.vino.models.entity;

import it.unimol.vino.models.enums.Sector;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Permission implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @Enumerated(EnumType.STRING)
    private Sector sector;

    @OneToMany(mappedBy = "permission", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<UserPermission> users;

    public Permission(Sector sector) {
        this.sector = sector;
    }
}
