package it.unimol.vino.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    private Sector sector;

    private Boolean canRead;

    private Boolean canWrite;

    private Boolean canDelete;

    private Boolean canUpdate;


}

