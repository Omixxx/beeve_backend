package it.unimol.vino.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import it.unimol.vino.models.entity.Item;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item,Long> {


    List<Item> findAllById(Long id);
}
