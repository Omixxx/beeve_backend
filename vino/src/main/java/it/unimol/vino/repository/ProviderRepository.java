package it.unimol.vino.repository;

import it.unimol.vino.models.entity.Provider;
import it.unimol.vino.models.response.ItemsProvidedByProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProviderRepository extends JpaRepository<Provider,Long> {

    Optional<Provider> findByEmail(String email);


    //  @Query("select new it.unimol.vino.models.response.ItemsProvidedByProvider(item.id,item.capacity,item.description,ProviderSupplyItem.quantity,ProviderSupplyItem.date) FROM item,ProviderSupplyItem WHERE ProviderSupplyItem.provider= :provider and ProviderSupplyItem.item=item")
    /*
    @Query("SELECT new it.unimol.vino.models.response.ItemsProvidedByProvider(item.id,item.capacity,item.description,ProviderSupplyItem.quantity,ProviderSupplyItem.date) FROM item,ProviderSupplyItem WHERE ProviderSupplyItem.provider= :p AND ProviderSupplyItem.item=item")
    List<ItemsProvidedByProvider> findProvidedItemById(Provider p);

    */
    @Query(nativeQuery = true)
    List<ItemsProvidedByProvider> findProvidedItemsById(Long id);
}
//item.id,item.capacity,item.description