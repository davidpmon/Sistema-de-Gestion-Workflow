package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "index"; // templates/index.html
    }
    @GetMapping("/terminos")
    public String terminos()
    {
        return "terminos";
    }
    @GetMapping("/admin")
    public String adminHome() {
        return "admin/home"; // templates/admin/home.html
    }

    @GetMapping("/medico")
    public String medicoHome() {
        return "medico/home"; // templates/medico/home.html
    }

    @GetMapping("/paciente")
    public String pacienteHome() {
        return "paciente/home"; // templates/paciente/home.html
    }

    @GetMapping("/autorizador")
    public String autorizadorHome() {
        return "autorizador/home"; // templates/autorizador/home.html
    }
}
