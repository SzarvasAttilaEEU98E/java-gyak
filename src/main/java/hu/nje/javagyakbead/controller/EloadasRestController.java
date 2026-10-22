
package hu.nje.javagyakbead.controller;

import hu.nje.javagyakbead.entity.Eloadas;
import hu.nje.javagyakbead.repository.EloadasRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/eloadasok")
public class EloadasRestController {

    private final EloadasRepository eloadasRepository;

    public EloadasRestController(EloadasRepository eloadasRepository) {
        this.eloadasRepository = eloadasRepository;
    }

    // Előadások listázása lapozással
    @GetMapping
    public Page<Eloadas> getEloadasok(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {

        // Lapozási paraméterek korlátozása
        int safePage = Math.max(page, 0);
        int safeSize = Math.max(1, Math.min(size, 100));

        // Előadások lekérdezése dátum szerint csökkenő sorrendben
        return eloadasRepository.findAll(
                PageRequest.of(
                        safePage,
                        safeSize,
                        Sort.by(
                                Sort.Order.desc("datum"),
                                Sort.Order.desc("id")
                        )
                )
        );
    }

    // Egy előadás lekérdezése azonosító alapján
    @GetMapping("/{id}")
    public ResponseEntity<Eloadas> getEloadasById(
            @PathVariable Long id
    ) {

        return eloadasRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
