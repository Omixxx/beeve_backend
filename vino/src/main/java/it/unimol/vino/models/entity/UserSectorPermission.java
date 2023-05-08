package it.unimol.vino.models.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
public class UserSectorPermission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    private Sector sector;

    private Boolean canRead;

    private Boolean canWrite;

    private Boolean canDelete;

    private Boolean canUpdate;

    public UserSectorPermission(User user, Sector sector, Boolean canRead, Boolean canWrite, Boolean canDelete, Boolean canUpdate) {
        this.user = user;
        this.sector = sector;
        this.canRead = canRead;
        this.canWrite = canWrite;
        this.canDelete = canDelete;
        this.canUpdate = canUpdate;
    }

}


