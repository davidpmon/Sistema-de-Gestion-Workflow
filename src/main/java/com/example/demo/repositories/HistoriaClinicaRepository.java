package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.models.HistoriaClinica;
import com.example.demo.models.Paciente;
import java.util.List;

public interface HistoriaClinicaRepository extends JpaRepository<HistoriaClinica, Integer> {
    List<HistoriaClinica> findByPaciente(Paciente paciente);
}