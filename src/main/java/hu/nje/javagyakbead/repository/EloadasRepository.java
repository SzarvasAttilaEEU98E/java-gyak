
package hu.nje.javagyakbead.repository;

import hu.nje.javagyakbead.entity.Eloadas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EloadasRepository extends JpaRepository<Eloadas, Long> {

    // Legnagyobb rögzített nézőszám egy adott moziban
    @Query("""
            SELECT MAX(e.nezoszam)
            FROM Eloadas e
            WHERE e.moziId = :moziId
            """)
    Integer findMaxNezoszamByMoziId(@Param("moziId") Long moziId);

    // Van-e előadás az adott filmhez?
    boolean existsByFilmId(Long filmId);

    // Van-e előadás az adott mozihoz?
    boolean existsByMoziId(Long moziId);
}
