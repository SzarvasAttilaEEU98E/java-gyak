
package hu.nje.javagyakbead.service;

import hu.nje.javagyakbead.entity.Eloadas;
import hu.nje.javagyakbead.repository.EloadasRepository;
import hu.nje.javagyakbead.request.EloadasRequest;
import hu.nje.javagyakbead.validation.EloadasValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EloadasService {

    private final EloadasRepository eloadasRepository;
    private final EloadasValidator eloadasValidator;

    public EloadasService(
            EloadasRepository eloadasRepository,
            EloadasValidator eloadasValidator) {

        this.eloadasRepository = eloadasRepository;
        this.eloadasValidator = eloadasValidator;
    }

    // Előadások lapozható lekérdezése
    public Page<Eloadas> getAllEloadasok(int page, int size) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by("id").ascending()
        );

        return eloadasRepository.findAll(pageable);
    }

    // Egy előadás lekérdezése ID alapján
    public Optional<Eloadas> getEloadasById(Long id) {
        return eloadasRepository.findById(id);
    }

    // Előadás létrehozása vagy módosítása
    public Eloadas saveEloadas(EloadasRequest request) {

        // Üzleti validáció
        eloadasValidator.validate(request);

        Eloadas eloadas;

        if (request.getId() == null) {

            // Új előadás létrehozása
            eloadas = new Eloadas();

        } else {

            // Meglévő előadás lekérdezése
            eloadas = eloadasRepository
                    .findById(request.getId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "A szerkesztett előadás nem létezik."
                    ));
        }

        // Request adatok átmásolása az entitásba
        eloadas.setFilmId(request.getFilmId());
        eloadas.setMoziId(request.getMoziId());
        eloadas.setDatum(request.getDatum());
        eloadas.setNezoszam(request.getNezoszam());
        eloadas.setBevetel(request.getBevetel());

        // Mentés az adatbázisba
        return eloadasRepository.save(eloadas);
    }

    // Előadás törlése
    public void deleteEloadas(Long id) {

        if (!eloadasRepository.existsById(id)) {
            throw new IllegalArgumentException(
                    "A törölni kívánt előadás nem létezik."
            );
        }

        eloadasRepository.deleteById(id);
    }
}
