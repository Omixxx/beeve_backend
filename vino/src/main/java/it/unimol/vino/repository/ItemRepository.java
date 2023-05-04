package it.unimol.vino.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import it.unimol.vino.models.entity.Item;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item,Long> {

}
