
package hu.nje.javagyakbead.service;

import hu.nje.javagyakbead.entity.Film;
import hu.nje.javagyakbead.repository.EloadasRepository;
import hu.nje.javagyakbead.repository.FilmRepository;
import hu.nje.javagyakbead.request.FilmRequest;
import hu.nje.javagyakbead.validation.FilmValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class FilmService {

    private final FilmRepository filmRepository;
    private final EloadasRepository eloadasRepository;
    private final FilmValidator filmValidator;

    public FilmService(
            FilmRepository filmRepository,
            EloadasRepository eloadasRepository,
            FilmValidator filmValidator) {

        this.filmRepository = filmRepository;
        this.eloadasRepository = eloadasRepository;
        this.filmValidator = filmValidator;
    }

    // Összes film lekérdezése
    public List<Film> getAllFilms() {
        return filmRepository.findAll();
    }

    // Egy film lekérdezése ID alapján
    public Optional<Film> getFilmById(Long id) {
        return filmRepository.findById(id);
    }

    // Film létrehozása vagy módosítása
    @Transactional
    public Film saveFilm(FilmRequest request) {

        // Üzleti validáció
        filmValidator.validate(request);

        Film film;

        if (request.getId() == null) {

            // Új film létrehozása
            film = new Film();

        } else {

            // Meglévő film lekérdezése
            film = filmRepository.findById(request.getId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "A szerkesztett film nem létezik."
                    ));
        }

        // Request adatok átmásolása az entitásba
        film.setCim(request.getCim());
        film.setEv(request.getEv());
        film.setHossz(request.getHossz());

        // Mentés az adatbázisba
        return filmRepository.save(film);
    }

    // Film biztonságos törlése
    @Transactional
    public void deleteFilm(Long id) {

        // Ellenőrizzük, hogy létezik-e a film
        if (!filmRepository.existsById(id)) {
            throw new IllegalArgumentException(
                    "A törölni kívánt film nem létezik."
            );
        }

        // Ellenőrizzük, hogy tartozik-e hozzá előadás
        if (eloadasRepository.existsByFilmId(id)) {
            throw new IllegalArgumentException(
                    "A film nem törölhető, mert előadások tartoznak hozzá."
            );
        }

        // Ha nincs kapcsolódó előadás, törölhető
        filmRepository.deleteById(id);
    }
}
