
package hu.nje.javagyakbead.service;

import hu.nje.javagyakbead.entity.Film;
import hu.nje.javagyakbead.repository.FilmRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FilmService {

    private final FilmRepository filmRepository;

    public FilmService(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    // Összes film lekérdezése
    public List<Film> getAllFilms() {
        return filmRepository.findAll();
    }

    // Egy film lekérdezése azonosító alapján
    public Optional<Film> getFilmById(Long id) {
        return filmRepository.findById(id);
    }

    // Új film létrehozása vagy meglévő módosítása
    public Film saveFilm(Film film) {
        return filmRepository.save(film);
    }

    // Film törlése azonosító alapján
    public void deleteFilm(Long id) {
        filmRepository.deleteById(id);
    }
}
