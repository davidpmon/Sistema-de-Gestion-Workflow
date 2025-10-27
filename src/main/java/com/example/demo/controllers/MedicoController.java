package com.example.demo.controllers;

import com.example.demo.models.*;
import com.example.demo.services.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/medico")
public class MedicoController {

    @Autowired
    private MedicoService medicoService;

    // ✅ Mostrar lista de pacientes o historial específico
    @GetMapping("/historial")
    public String verHistorial(@RequestParam(value = "id", required = false) Integer idPaciente, Model model) {
        if (idPaciente != null) {
            // 🔹 Mostrar historial de un paciente específico
            List<HistoriaClinica> historias = medicoService.obtenerHistoriasPorPaciente(idPaciente);
            Paciente paciente = medicoService.obtenerPacientePorId(idPaciente);
            model.addAttribute("paciente", paciente);
            model.addAttribute("historias", historias);
        } else {
            // 🔹 Mostrar lista de todos los pacientes disponibles
            List<Paciente> pacientes = medicoService.obtenerTodosLosPacientes();
            model.addAttribute("pacientes", pacientes);
        }
        return "medico/historial";
    }

    // ✅ Mostrar órdenes médicas del médico autenticado
    @GetMapping("/ordenes")
    public String verOrdenes(@AuthenticationPrincipal Usuario medico, Model model) {
        List<OrdenMedica> ordenes = medicoService.obtenerOrdenesPorMedico(medico);
        model.addAttribute("ordenes", ordenes);
        return "medico/ordenes";
    }

    // ✅ Crear nueva orden
    @PostMapping("/ordenes/nueva")
    public String crearOrden(@ModelAttribute OrdenMedica orden) {
        medicoService.crearOrden(orden);
        return "redirect:/medico/ordenes";
    }

    // ✅ Corregir orden existente
    @PostMapping("/ordenes/corregir/{id}")
    public String corregirOrden(@PathVariable Integer id, @RequestParam String descripcion) {
        medicoService.corregirOrden(id, descripcion);
        return "redirect:/medico/ordenes";
    }
}
