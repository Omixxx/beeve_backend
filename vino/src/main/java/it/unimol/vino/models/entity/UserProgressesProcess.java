package it.unimol.vino.models.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Entity
@Getter
public class UserProgressesProcess {
    @Id
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Process process;

    @Column(name = "description", nullable = false, updatable = false)
    private String description;

}
