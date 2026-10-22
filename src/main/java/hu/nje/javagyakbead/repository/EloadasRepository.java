
package hu.nje.javagyakbead.repository;

import hu.nje.javagyakbead.dto.EloadasListDto;
import hu.nje.javagyakbead.entity.Eloadas;
import hu.nje.javagyakbead.entity.Film;
import hu.nje.javagyakbead.entity.Mozi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
}
