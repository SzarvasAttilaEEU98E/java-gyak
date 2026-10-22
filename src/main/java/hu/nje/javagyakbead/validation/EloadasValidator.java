
package hu.nje.javagyakbead.validation;

import hu.nje.javagyakbead.entity.Mozi;
import hu.nje.javagyakbead.repository.FilmRepository;
import hu.nje.javagyakbead.repository.MoziRepository;
import hu.nje.javagyakbead.request.EloadasRequest;
import org.springframework.stereotype.Component;

@Component
public class EloadasValidator {

    private final FilmRepository filmRepository;
    private final MoziRepository moziRepository;

    public EloadasValidator(
            FilmRepository filmRepository,
            MoziRepository moziRepository) {

        this.filmRepository = filmRepository;
        this.moziRepository = moziRepository;
    }

    public void validate(EloadasRequest request) {

        // Film létezésének ellenőrzése
        if (request.getFilmId() == null ||
                !filmRepository.existsById(request.getFilmId())) {

            throw new IllegalArgumentException(
                    "A kiválasztott film nem létezik."
            );
        }

        // Mozi létezésének ellenőrzése
        if (request.getMoziId() == null) {

            throw new IllegalArgumentException(
                    "A mozi kiválasztása kötelező."
            );
        }

        Mozi mozi = moziRepository
                .findById(request.getMoziId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "A kiválasztott mozi nem létezik."
                ));

        // Mozi férőhelyadatának ellenőrzése
        if (mozi.getFerohely() == null ||
                mozi.getFerohely() < 0) {

            throw new IllegalArgumentException(
                    "A mozi férőhelyadata érvénytelen."
            );
        }

        // Nézőszám nem haladhatja meg a férőhelyet
        if (request.getNezoszam() != null &&
                request.getNezoszam() > mozi.getFerohely()) {

            throw new IllegalArgumentException(
                    "A nézőszám nem haladhatja meg a mozi "
                            + "férőhelyét! Maximum: "
                            + mozi.getFerohely() + " fő."
            );
        }
    }
}
