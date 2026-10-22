
package hu.nje.javagyakbead.controller;

import hu.nje.javagyakbead.dto.EloadasListDto;
import hu.nje.javagyakbead.service.EloadasService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/eloadasok")
public class EloadasController {

    private final EloadasService eloadasService;

    public EloadasController(EloadasService eloadasService) {
        this.eloadasService = eloadasService;
    }

    // Előadások lapozható listázása
    @GetMapping
    public String listEloadasok(
            @RequestParam(defaultValue = "0") int page,
            Model model) {

        // Lapozás beállítása: 20 előadás oldalanként
        Pageable pageable = PageRequest.of(
                Math.max(page, 0),
                20,
                Sort.by("id").ascending()
        );

        // Előadások lekérdezése filmcímmel és mozinévvel
        Page<EloadasListDto> eloadasok =
                eloadasService.getAllEloadasokWithDetails(pageable);

        // Adatok átadása a Thymeleaf sablonnak
        model.addAttribute("eloadasok", eloadasok);
        model.addAttribute("currentPage", eloadasok.getNumber());
        model.addAttribute("totalPages", eloadasok.getTotalPages());

        return "eloadasok";
    }
}
