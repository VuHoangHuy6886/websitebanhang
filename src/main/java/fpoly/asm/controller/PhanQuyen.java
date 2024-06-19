package fpoly.asm.controller;

import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PhanQuyen {
    @GetMapping("/login")
    public String showFormLogin() {
        return "login";
    }

    @GetMapping("/access-denied")
    public String showEroors() {
        return "access-denied";
    }
}
