package it.unimol.vino.models.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
@EqualsAndHashCode
public class ProviderSupplyItemId implements Serializable {
    private Provider provider;

    private Item item;
}
