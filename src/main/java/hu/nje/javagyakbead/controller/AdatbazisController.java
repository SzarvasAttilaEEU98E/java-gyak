
package hu.nje.javagyakbead.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdatbazisController {

    // Adatbázis áttekintő oldal megjelenítése
    @GetMapping("/adatbazis")
    public String adatbazis() {
        return "adatbazis";
    }
}
