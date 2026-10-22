
package hu.nje.javagyakbead.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RestPageController {

    // REST API bemutatóoldal megjelenítése
    @GetMapping("/rest")
    public String restPage() {
        return "rest";
    }
}
