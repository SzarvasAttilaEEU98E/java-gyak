
package hu.nje.javagyakbead.service;

import hu.nje.javagyakbead.repository.EloadasRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiagramService {

    private final EloadasRepository eloadasRepository;

    public DiagramService(EloadasRepository eloadasRepository) {
        this.eloadasRepository = eloadasRepository;
    }

    // Előadások számának lekérdezése mozinként
    public List<Object[]> getEloadasokSzamaMozinkent() {
        return eloadasRepository.countEloadasokByMozi();
    }

    // A 10 legnagyobb bevételű mozi lekérdezése
    public List<Object[]> getTop10MoziBevetel() {
        return eloadasRepository.findTopMozikByBevetel(
                PageRequest.of(0, 10)
        );
    }
}
