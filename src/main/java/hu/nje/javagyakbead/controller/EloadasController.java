
package hu.nje.javagyakbead.controller;

import hu.nje.javagyakbead.entity.Eloadas;
import hu.nje.javagyakbead.service.EloadasService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EloadasController {

    private final EloadasService eloadasService;

    public EloadasController(EloadasService eloadasService) {
        this.eloadasService = eloadasService;
    }

    @GetMapping("/eloadasok")
    public String getEloadasok(
            @RequestParam(defaultValue = "0") int page,
            Model model) {

        int pageSize = 20;

        page = Math.max(page, 0);

        Page<Eloadas> eloadasok =
                eloadasService.getAllEloadasok(page, pageSize);

        model.addAttribute("eloadasok", eloadasok.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", eloadasok.getTotalPages());
        model.addAttribute("totalElements", eloadasok.getTotalElements());

        return "eloadasok";
    }
}
