package it.unimol.vino.models.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
public class UserProgressesProcess {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Process process;

    @Column(nullable = false, updatable = false)
    private String description;

    public UserProgressesProcess(User user, Process process, String description) {
        this.description = description;
        this.user = user;
        this.process = process;
    }
}
