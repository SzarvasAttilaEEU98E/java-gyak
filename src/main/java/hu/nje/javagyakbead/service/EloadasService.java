
package hu.nje.javagyakbead.service;

import hu.nje.javagyakbead.dto.EloadasListDto;
import hu.nje.javagyakbead.entity.Eloadas;
import hu.nje.javagyakbead.repository.EloadasRepository;
import hu.nje.javagyakbead.request.EloadasRequest;
import hu.nje.javagyakbead.validation.EloadasValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    // Előadások lapozható listázása
    @Transactional(readOnly = true)
    public Page<Eloadas> getAllEloadasok(Pageable pageable) {

        return eloadasRepository.findAll(pageable);
    }

    // Előadások listázása filmcímmel és mozinévvel
    @Transactional(readOnly = true)
    public Page<EloadasListDto> getAllEloadasokWithDetails(
            Pageable pageable) {

        return eloadasRepository.findAllWithDetails(pageable);
    }

    // Egy előadás lekérdezése ID alapján
    @Transactional(readOnly = true)
    public Optional<Eloadas> getEloadasById(Long id) {

        return eloadasRepository.findById(id);
    }

    // Előadás létrehozása vagy módosítása
    @Transactional
    public Eloadas saveEloadas(EloadasRequest request) {

        // Üzleti validáció
        eloadasValidator.validate(request);

        Eloadas eloadas;

        if (request.getId() == null) {

            // Új előadás létrehozása
            eloadas = new Eloadas();

        } else {

            // Meglévő előadás lekérdezése
            eloadas = eloadasRepository.findById(request.getId())
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

        return eloadasRepository.save(eloadas);
    }

    // Előadás törlése
    @Transactional
    public void deleteEloadas(Long id) {

        if (!eloadasRepository.existsById(id)) {
            throw new IllegalArgumentException(
                    "A törölni kívánt előadás nem létezik."
            );
        }

        eloadasRepository.deleteById(id);
    }
}
