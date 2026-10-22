
package hu.nje.javagyakbead.service;

import hu.nje.javagyakbead.entity.Mozi;
import hu.nje.javagyakbead.repository.MoziRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MoziService {

    private final MoziRepository moziRepository;

    public MoziService(MoziRepository moziRepository) {
        this.moziRepository = moziRepository;
    }

    // Összes mozi lekérdezése
    public List<Mozi> getAllMozik() {
        return moziRepository.findAll();
    }

    // Egy mozi lekérdezése azonosító alapján
    public Optional<Mozi> getMoziById(Long id) {
        return moziRepository.findById(id);
    }

    // Új mozi létrehozása vagy meglévő módosítása
    public Mozi saveMozi(Mozi mozi) {
        return moziRepository.save(mozi);
    }

    // Mozi törlése azonosító alapján
    public void deleteMozi(Long id) {
        moziRepository.deleteById(id);
    }
}
