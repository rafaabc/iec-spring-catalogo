package pro.gsilva.catalogo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NotFoundController {
    @GetMapping("/not-found")
    public String notFound() {
        return "error-404";
    }
}
