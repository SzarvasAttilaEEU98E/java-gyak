
package hu.nje.javagyakbead.repository;

import hu.nje.javagyakbead.dto.EloadasListDto;
import hu.nje.javagyakbead.entity.Eloadas;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EloadasRepository extends JpaRepository<Eloadas, Long> {

    // Legnagyobb rögzített nézőszám egy adott moziban
    @Query("""
            SELECT MAX(e.nezoszam)
            FROM Eloadas e
            WHERE e.moziId = :moziId
            """)
    Integer findMaxNezoszamByMoziId(
            @Param("moziId") Long moziId
    );

    // Van-e előadás az adott filmhez?
    boolean existsByFilmId(Long filmId);

    // Van-e előadás az adott mozihoz?
    boolean existsByMoziId(Long moziId);

    // Előadások listázása filmcímmel és mozinévvel
    @Query(
            value = """
                    SELECT new hu.nje.javagyakbead.dto.EloadasListDto(
                        e.id,
                        f.cim,
                        m.nev,
                        e.datum,
                        e.nezoszam,
                        e.bevetel
                    )
                    FROM Eloadas e
                    JOIN Film f ON e.filmId = f.id
                    JOIN Mozi m ON e.moziId = m.id
                    """,
            countQuery = """
                    SELECT COUNT(e)
                    FROM Eloadas e
                    JOIN Film f ON e.filmId = f.id
                    JOIN Mozi m ON e.moziId = m.id
                    """
    )
    Page<EloadasListDto> findAllWithDetails(Pageable pageable);

    // Előadások számának lekérdezése mozinként
    @Query("""
            SELECT m.nev, COUNT(e.id)
            FROM Mozi m
            LEFT JOIN Eloadas e ON e.moziId = m.id
            GROUP BY m.id, m.nev
            ORDER BY m.nev
            """)
    List<Object[]> countEloadasokByMozi();

    // A 10 legnagyobb bevételű mozi lekérdezése
    @Query("""
            SELECT m.nev, COALESCE(SUM(e.bevetel), 0)
            FROM Mozi m
            LEFT JOIN Eloadas e ON e.moziId = m.id
            GROUP BY m.id, m.nev
            ORDER BY COALESCE(SUM(e.bevetel), 0) DESC
            """)
    List<Object[]> findTopMozikByBevetel(Pageable pageable);
}
