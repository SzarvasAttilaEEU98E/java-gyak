
package hu.nje.javagyakbead.service;

import hu.nje.javagyakbead.entity.Mozi;
import hu.nje.javagyakbead.repository.EloadasRepository;
import hu.nje.javagyakbead.repository.MoziRepository;
import hu.nje.javagyakbead.request.MoziRequest;
import hu.nje.javagyakbead.validation.MoziValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MoziService {

    private final MoziRepository moziRepository;
    private final EloadasRepository eloadasRepository;
    private final MoziValidator moziValidator;

    public MoziService(
            MoziRepository moziRepository,
            EloadasRepository eloadasRepository,
            MoziValidator moziValidator) {

        this.moziRepository = moziRepository;
        this.eloadasRepository = eloadasRepository;
        this.moziValidator = moziValidator;
    }

    // Összes mozi lekérdezése
    public List<Mozi> getAllMozik() {
        return moziRepository.findAll();
    }

    // Egy mozi lekérdezése ID alapján
    public Optional<Mozi> getMoziById(Long id) {
        return moziRepository.findById(id);
    }

    // Mozi létrehozása vagy módosítása
    @Transactional
    public Mozi saveMozi(MoziRequest request) {

        Mozi mozi;

        if (request.getId() == null) {

            // Új mozi létrehozása
            mozi = new Mozi();

        } else {

            // Meglévő mozi lekérdezése
            mozi = moziRepository.findById(request.getId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "A szerkesztett mozi nem létezik."
                    ));
        }

        // Üzleti validáció
        moziValidator.validate(request);

        // Request adatok átmásolása az entitásba
        mozi.setNev(request.getNev());
        mozi.setVaros(request.getVaros());
        mozi.setFerohely(request.getFerohely());

        // Mentés az adatbázisba
        return moziRepository.save(mozi);
    }

    // Mozi biztonságos törlése
    @Transactional
    public void deleteMozi(Long id) {

        // Ellenőrizzük, hogy létezik-e a mozi
        if (!moziRepository.existsById(id)) {
            throw new IllegalArgumentException(
                    "A törölni kívánt mozi nem létezik."
            );
        }

        // Ellenőrizzük, hogy tartozik-e hozzá előadás
        if (eloadasRepository.existsByMoziId(id)) {
            throw new IllegalArgumentException(
                    "A mozi nem törölhető, mert előadások tartoznak hozzá."
            );
        }

        // Ha nincs kapcsolódó előadás, törölhető
        moziRepository.deleteById(id);
    }
}
