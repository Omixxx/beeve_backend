package it.unimol.vino.models.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ProviderSupplyItemId implements Serializable {
    private Provider provider;
    private Item item;
}
