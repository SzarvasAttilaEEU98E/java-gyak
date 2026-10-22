
package hu.nje.javagyakbead.service;

import hu.nje.javagyakbead.entity.Eloadas;
import hu.nje.javagyakbead.repository.EloadasRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EloadasService {

    private final EloadasRepository eloadasRepository;

    public EloadasService(EloadasRepository eloadasRepository) {
        this.eloadasRepository = eloadasRepository;
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

    // Egy előadás lekérdezése azonosító alapján
    public Optional<Eloadas> getEloadasById(Long id) {
        return eloadasRepository.findById(id);
    }

    // Új előadás létrehozása vagy meglévő módosítása
    public Eloadas saveEloadas(Eloadas eloadas) {
        return eloadasRepository.save(eloadas);
    }

    // Előadás törlése azonosító alapján
    public void deleteEloadas(Long id) {
        eloadasRepository.deleteById(id);
    }
}
