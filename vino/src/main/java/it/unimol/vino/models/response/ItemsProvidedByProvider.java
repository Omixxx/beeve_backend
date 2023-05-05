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

    private Integer quantity;

    private Date date;

}
