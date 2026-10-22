
package hu.nje.javagyakbead.repository;

import hu.nje.javagyakbead.entity.Uzenet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UzenetRepository extends JpaRepository<Uzenet, Long> {

    // Üzenetek lekérdezése fordított időrendben
    List<Uzenet> findAllByOrderByKuldesIdejeDesc();

}
