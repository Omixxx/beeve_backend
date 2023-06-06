package it.unimol.vino.repository;

import it.unimol.vino.models.entity.Category;
import it.unimol.vino.models.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findAllByCategory(Category category);
}
