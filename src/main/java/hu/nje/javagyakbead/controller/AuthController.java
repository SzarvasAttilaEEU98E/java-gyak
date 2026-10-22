
package hu.nje.javagyakbead.controller;

import hu.nje.javagyakbead.entity.User;
import hu.nje.javagyakbead.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository,
                          PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Bejelentkezési oldal
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // Regisztrációs oldal
    @GetMapping("/register")
    public String register() {
        return "register";
    }

    // Regisztráció feldolgozása
    @PostMapping("/register")
    public String registerUser(
            @RequestParam String username,
            @RequestParam String password,
            Model model) {

        // Felhasználónév ellenőrzése
        if (userRepository.findByUsername(username).isPresent()) {
            model.addAttribute("error", "Ez a felhasználónév már foglalt!");
            return "register";
        }

        // Új felhasználó létrehozása
        User user = new User();

        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole("USER");

        // Mentés az adatbázisba
        userRepository.save(user);

        return "redirect:/login?registered";
    }
}
