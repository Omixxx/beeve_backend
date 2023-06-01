package it.unimol.vino.repository;

import it.unimol.vino.models.entity.Category;
import it.unimol.vino.models.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    Optional<Item> findByCategoryAndCapacityAndName(Category category, Float capacity, String name);

    Optional<Item> findItemByNameAndCategoryAndCapacity(Category category, String name, Float capacity);

    List<Item> findAllByCategory(Category category);

}
