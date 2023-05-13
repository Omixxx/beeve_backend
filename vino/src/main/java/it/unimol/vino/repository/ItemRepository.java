package it.unimol.vino.repository;

import it.unimol.vino.models.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import it.unimol.vino.models.entity.Item;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item,Long> {

    Optional<Item> findByCategoryAndCapacityAndName(Category category, Long capacity,String name);

    List<Item> findAllByCategory(Category category);

}
