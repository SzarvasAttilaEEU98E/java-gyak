
package hu.nje.javagyakbead.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    // Főoldal
    @GetMapping("/")
    public String home() {
        return "home";
    }

    // Felhasználói oldal
    @GetMapping("/user")
    public String user() {
        return "user";
    }

    // Adminisztrációs oldal
    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }
}
