
package hu.nje.javagyakbead.controller;

import hu.nje.javagyakbead.entity.Mozi;
import hu.nje.javagyakbead.repository.MoziRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mozik")
public class MoziRestController {

    private final MoziRepository moziRepository;

    public MoziRestController(MoziRepository moziRepository) {
        this.moziRepository = moziRepository;
    }

    // Mozik listázása lapozással
    @GetMapping
    public Page<Mozi> getMozik(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        // Lapozási paraméterek korlátozása
        int safePage = Math.max(page, 0);
        int safeSize = Math.max(1, Math.min(size, 100));

        return moziRepository.findAll(
                PageRequest.of(safePage, safeSize)
        );
    }

    // Egy mozi lekérdezése azonosító alapján
    @GetMapping("/{id}")
    public ResponseEntity<Mozi> getMoziById(
            @PathVariable Long id
    ) {

        return moziRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
