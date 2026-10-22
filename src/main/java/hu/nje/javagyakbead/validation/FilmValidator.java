
package hu.nje.javagyakbead.validation;

import hu.nje.javagyakbead.request.FilmRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class FilmValidator {

    public void validate(FilmRequest request) {

        // Megjelenési év ellenőrzése
        if (request.getEv() != null &&
                request.getEv() > LocalDate.now().getYear()) {

            throw new IllegalArgumentException(
                    "A film megjelenési éve nem lehet későbbi " +
                            "az aktuális évnél."
            );
        }

    }
}
