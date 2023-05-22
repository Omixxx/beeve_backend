package it.unimol.vino.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import it.unimol.vino.models.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;


@Builder
@AllArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryDTO {

    private String name;

    private List<Item> itemList;

    private Boolean isPrimary;

}

