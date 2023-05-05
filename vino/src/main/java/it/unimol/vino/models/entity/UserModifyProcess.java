package it.unimol.vino.models.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.Date;

@Entity
@Getter
public class UserModifyProcess {

    @Id
    @ManyToOne
    private User user;

    @Id
    @ManyToOne
    private Process process;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date", nullable = false, updatable = false)
    private Date date;

    @Column(name = "description", nullable = false, updatable = false)
    private String description;

}
