
package hu.nje.javagyakbead.controller;

import hu.nje.javagyakbead.service.DiagramService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DiagramController {

    private final DiagramService diagramService;

    public DiagramController(DiagramService diagramService) {
        this.diagramService = diagramService;
    }

    // Diagram oldal megjelenítése
    @GetMapping("/diagram")
    public String diagram(Model model) {

        // A 10 legnagyobb bevételű mozi lekérdezése
        List<Object[]> adatok =
                diagramService.getTop10MoziBevetel();

        // Adatok átadása a Thymeleaf sablonnak
        model.addAttribute("diagramAdatok", adatok);

        return "diagram";
    }
}
