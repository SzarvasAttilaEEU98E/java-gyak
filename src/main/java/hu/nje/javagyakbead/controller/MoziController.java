
package hu.nje.javagyakbead.controller;

import hu.nje.javagyakbead.service.MoziService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MoziController {

    private final MoziService moziService;

    public MoziController(MoziService moziService) {
        this.moziService = moziService;
    }

    @GetMapping("/mozik")
    public String getMozik(Model model) {
        model.addAttribute("mozik", moziService.getAllMozik());

        return "mozik";
    }
}
