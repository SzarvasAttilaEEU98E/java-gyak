
package hu.nje.javagyakbead.validation;

import hu.nje.javagyakbead.repository.EloadasRepository;
import hu.nje.javagyakbead.request.MoziRequest;
import org.springframework.stereotype.Component;

@Component
public class MoziValidator {

    private final EloadasRepository eloadasRepository;

    public MoziValidator(EloadasRepository eloadasRepository) {
        this.eloadasRepository = eloadasRepository;
    }

    public void validate(MoziRequest request) {

        // Új mozinál még nincs kapcsolódó előadás
        if (request.getId() == null) {
            return;
        }

        // Hiányzó férőhelyet a Request validáció kezeli
        if (request.getFerohely() == null) {
            return;
        }

        // A mozihoz tartozó legnagyobb nézőszám
        Integer maxNezoszam =
                eloadasRepository.findMaxNezoszamByMoziId(request.getId());

        // Ha nincs előadás, nincs további ellenőrzés
        if (maxNezoszam == null) {
            return;
        }

        // Férőhelyszám ellenőrzése
        if (request.getFerohely() < maxNezoszam) {

            throw new IllegalArgumentException(
                    "A mozi férőhelyszáma nem lehet kisebb, " +
                            "mint a már rögzített előadások legnagyobb " +
                            "nézőszáma (" + maxNezoszam + " fő)."
            );
        }
    }
}
