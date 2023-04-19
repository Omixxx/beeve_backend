package it.unimol.vino.models.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSectorPermission implements Serializable {

    @Id
    @ManyToOne
    private User user;

    @Id
    @ManyToOne
    private Sector sector;

    private Boolean canRead;

    private Boolean canWrite;

    private Boolean canDelete;

    private Boolean canUpdate;


}

