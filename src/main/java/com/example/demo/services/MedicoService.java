package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import com.example.demo.models.*;
import com.example.demo.repositories.*;

@Service
public class MedicoService {

    @Autowired
    private HistoriaClinicaRepository historiaRepo;

    @Autowired
    private OrdenMedicaRepository ordenRepo;

    @Autowired
    private PacienteRepository pacienteRepo;

    // Listar historias clínicas de un paciente existente
    public List<HistoriaClinica> obtenerHistoriasPorPaciente(Integer idPaciente) {
        Optional<Paciente> pacienteOpt = pacienteRepo.findById(idPaciente);
        if (pacienteOpt.isPresent()) {
            return historiaRepo.findByPaciente(pacienteOpt.get());
        }
        return List.of(); // retorna lista vacía si no existe el paciente
    }

    // Crear nueva orden médica
    public OrdenMedica crearOrden(OrdenMedica orden) {
        orden.setEstado(OrdenMedica.EstadoOrden.PENDIENTE);
        orden.setFecha(LocalDateTime.now()); // 🔹 corregido: ahora coincide con LocalDateTime
        return ordenRepo.save(orden);
    }

    // Corregir orden médica
    public OrdenMedica corregirOrden(Integer id, String nuevaDescripcion) {
        Optional<OrdenMedica> ordenOpt = ordenRepo.findById(id);
        if (ordenOpt.isPresent()) {
            OrdenMedica orden = ordenOpt.get();
            orden.setDescripcion(nuevaDescripcion);
            orden.setEstado(OrdenMedica.EstadoOrden.CORREGIDA);
            return ordenRepo.save(orden);
        }
        return null;
    }

    // Listar órdenes por médico
    public List<OrdenMedica> obtenerOrdenesPorMedico(Usuario medico) {
        return ordenRepo.findByMedico(medico);
    }

    // Listar todos los pacientes (para el historial)
    public List<Paciente> obtenerTodosLosPacientes() {
        return pacienteRepo.findAll();
    }

    // Obtener un paciente por su ID
    public Paciente obtenerPacientePorId(Integer idPaciente) {
        return pacienteRepo.findById(idPaciente).orElse(null);
    }
}
