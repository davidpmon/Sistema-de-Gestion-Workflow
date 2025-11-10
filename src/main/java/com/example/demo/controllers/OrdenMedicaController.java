package com.example.demo.controllers;
import com.example.demo.dto.HistoriaClinicaDTO;
import com.example.demo.models.Paciente;
import com.example.demo.models.OrdenMedica;
import com.example.demo.models.Usuario;
import com.example.demo.models.HistoriaClinica;
import com.example.demo.repositories.OrdenMedicaRepository;
import com.example.demo.repositories.UsuarioRepository;
import com.example.demo.repositories.HistoriaClinicaRepository;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/medico/ordenes")
public class OrdenMedicaController {

    @Autowired
    private OrdenMedicaRepository ordenRepo;

    @Autowired
    private UsuarioRepository usuarioRepo;

    @Autowired
    private HistoriaClinicaRepository historiaRepo;

    // Mostrar lista de órdenes del médico logueado
    @GetMapping
    public String listarOrdenes(Model model, Authentication authentication) {
        String cedula = authentication.getName(); // obtenida del login
        Usuario medico = usuarioRepo.findByCedula(cedula).orElse(null);

        if (medico == null) {
            return "redirect:/login?errorUsuarioNoEncontrado";
        }

        List<OrdenMedica> ordenes = ordenRepo.findByMedico(medico);
        List<HistoriaClinica> historias = historiaRepo.findAll();

        model.addAttribute("ordenes", ordenes);
        model.addAttribute("historias", historias);
        return "medico/ordenes"; // tu plantilla HTML
    }
    // Buscar historia clínica por ID (para autocompletar)
    @GetMapping("/buscar-historia/{id}")
    @ResponseBody
    public ResponseEntity<?> obtenerHistoria(@PathVariable Integer id) {
        var historia = historiaRepo.findById(id).orElse(null);

        if (historia == null) {
            return ResponseEntity.status(404).body("Historia clínica no encontrada");
        }

        var paciente = historia.getPaciente();
        var usuario = paciente.getUsuario();

        var dto = new HistoriaClinicaDTO(
                historia.getIdHistoria(),
                usuario.getNombre(),
                usuario.getApellido(),
                paciente.getNumeroDocumento(),
                paciente.getFechaNacimiento()
        );

        return ResponseEntity.ok(dto);
    }


    // Crear nueva orden médica
    @PostMapping("/nueva")
    public String crearOrden(
            @RequestParam("descripcion") String descripcion,
            @RequestParam("id_historia") Integer idHistoria,
            Authentication authentication) {

        String cedula = authentication.getName();
        Usuario medico = usuarioRepo.findByCedula(cedula).orElse(null);
        HistoriaClinica historia = historiaRepo.findById(idHistoria).orElse(null);

        if (medico == null || historia == null) {
            return "redirect:/medico/ordenes?error";
        }


        Paciente paciente = historia.getPaciente(); //traemos el paciente desde la historia

        System.out.println("Paciente a asignar: " + paciente);
        System.out.println("Paciente ID: " + (paciente != null ? paciente.getIdPaciente() : "null"));


        OrdenMedica orden = new OrdenMedica();
        orden.setDescripcion(descripcion);
        orden.setFecha(LocalDateTime.now());
        orden.setEstado(OrdenMedica.EstadoOrden.PENDIENTE);
        orden.setMedico(medico);
        orden.setHistoria(historia);
        orden.setPaciente(paciente);

        ordenRepo.save(orden);
        System.out.println("Paciente asignado en orden: " + orden.getPaciente());

        return "redirect:/medico/ordenes?success";
    }
}