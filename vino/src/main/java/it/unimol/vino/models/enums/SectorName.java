package it.unimol.vino.models.enums;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum SectorName {
    CONFERIMENTO,
    @JsonProperty("SECTOR")
    FORNITURA,
    @JsonProperty("SECTOR")
    MAGAZZINO,
    @JsonProperty("SECTOR")
    PROCESSO
}
