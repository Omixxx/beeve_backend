package it.unimol.vino.dto;


import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ProviderDTO(Long id,
                          String name,
                          String phone_number,
                          String email,
                          String address) {
}
