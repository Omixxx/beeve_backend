package it.unimol.vino.models.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ProcessUseItemId implements Serializable {

    private Process process;
    private Item item;
}
