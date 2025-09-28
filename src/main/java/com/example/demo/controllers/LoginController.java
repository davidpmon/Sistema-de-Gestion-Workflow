package com.example.demo.controllers;//indicacion de que esta dentro de la carpeta controllers

import com.example.demo.services.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController
{

    private final LoginService loginService;

    public LoginController(LoginService loginService)
    {
        this.loginService = loginService;
    }

    @GetMapping("/login")
    public String loginPage()
    {
        return "login"; // Thymeleaf buscará login.html
    }

    @PostMapping("/login")
    public String login(@RequestParam String cedula,
                        @RequestParam String contraseña,
                        Model model)
    {
        return loginService.login(cedula, contraseña)
                .map(user ->
                {
                    String rol = user.getRol().getNombreRol();
                    switch (rol)
                    {
                        case "Administrador": return "redirect:/admin";
                        case "Médico": return "redirect:/medico";
                        case "Autorizador": return "redirect:/autorizador";
                        case "Paciente": return "redirect:/paciente";
                        default: return "login";
                    }
                })
                .orElse("login");
    }
}
