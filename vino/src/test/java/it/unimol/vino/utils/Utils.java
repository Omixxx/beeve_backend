package it.unimol.vino.utils;

import it.unimol.vino.models.entity.Provider;
import it.unimol.vino.repository.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("h2")
public class Utils {
    @Autowired
    private ProviderRepository providerRepository;
    public  void savePorovider(){
    Provider provider = Provider.builder().id(12734L).email("a.a@g.com").build();
        providerRepository.save(provider);}
}
