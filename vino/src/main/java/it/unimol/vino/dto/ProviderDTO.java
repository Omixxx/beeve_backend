package it.unimol.vino.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProviderDTO {

    private Long id;

    private String name;

    private String phone_number;

    private String email;

    private String address;

    private String website_url;

    public static ProviderDTO getFullProviderDTO(@NonNull it.unimol.vino.models.entity.Provider provider){
        return ProviderDTO.builder()
                .id(provider.getId())
                .name(provider.getName())
                .phone_number(provider.getPhone_number())
                .email(provider.getEmail())
                .address(provider.getAddress())
                .website_url(provider.getWebsite_url())
                .build();
    }

    public static ProviderDTO getName(@NonNull it.unimol.vino.models.entity.Provider provider){
        return ProviderDTO.builder()
                .name(provider.getName())
                .build();
    }

    public static ProviderDTO getNameNumberEmail(@NonNull it.unimol.vino.models.entity.Provider provider){
        return ProviderDTO.builder()
                .name(provider.getName())
                .phone_number(provider.getPhone_number())
                .email(provider.getEmail())
                .build();
    }

}
