
package hu.nje.javagyakbead.controller;

import hu.nje.javagyakbead.entity.Film;
import hu.nje.javagyakbead.repository.FilmRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/filmek")
public class FilmRestController {

    private final FilmRepository filmRepository;

    public FilmRestController(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    // Filmek listázása lapozással
    @GetMapping
    public Page<Film> getFilmek(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        // Lapméret korlátozása
        int safePage = Math.max(page, 0);
        int safeSize = Math.max(1, Math.min(size, 100));

        return filmRepository.findAll(
                PageRequest.of(safePage, safeSize)
        );
    }

    // Egy film lekérdezése azonosító alapján
    @GetMapping("/{id}")
    public ResponseEntity<Film> getFilmById(
            @PathVariable Long id
    ) {

        return filmRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
