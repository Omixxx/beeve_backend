package it.unimol.vino.models.response;


import lombok.*;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ItemsProvidedByProvider {

    private Long id;

    private Long capacity;

    private String description;

    private Long quantity;

    private Date date;
}
