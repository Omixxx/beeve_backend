package it.unimol.vino.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.Date;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "contribution")
@Table(name = "contribution")
@EqualsAndHashCode()
public class Contribution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Il contributo deve avere un' origine")
    @Column(name = "origin")
    private String origin;

    @NotBlank(message = "Il campo nazione non può essere vuoto")
    @Column(name = "country")
    private String country;

    //TODO: valutare se è possibile inserire un tipo "immagine"
    @Column(name = "photo_url")
    private String photoURL;

    @Column(name = "description")
    private String description;

    @Positive(message = "Il contributo deve avere un grado zuccherino positivo")
    @Column(name = "sugar_degree")
    private double sugarDegree;

    @Positive(message = "Il contributo deve avere una quantità positiva")
    @Column(name = "quantity")
    private double quantity;

    @JoinColumn(name = "grape_type")
    @ManyToOne
    private GrapeType associatedGrapeType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "submitted_by", nullable = false, updatable = false)
    private User submitter;

    @Temporal(TemporalType.TIMESTAMP)
    private Date submissionDate;
}

