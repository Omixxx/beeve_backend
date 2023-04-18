package it.unimol.vino.models.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class UserPermission implements Serializable {

    @Id
    @ManyToOne
    private User user;

    @Id
    @ManyToOne
    private Permission permission;

    private Boolean canRead;

    private Boolean canWrite;

    private Boolean canDelete;

    private Boolean canUpdate;


    public UserPermission(User user, Permission permission) {
        this.user = user;
        this.permission = permission;
        this.canRead = true;
        this.canWrite = false;
        this.canDelete = false;
        this.canUpdate = false;
    }

}

