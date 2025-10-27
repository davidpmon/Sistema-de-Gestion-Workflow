package com.example.demo.models;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "historias_clinicas")
public class HistoriaClinica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_historia;

    @ManyToOne
    @JoinColumn(name = "id_paciente", nullable = false)
    private Paciente paciente;

    private LocalDate fecha;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(columnDefinition = "TEXT")
    private String diagnostico;

    @OneToMany(mappedBy = "historia", cascade = CascadeType.ALL)
    private List<OrdenMedica> ordenes;

    // Getters y setters
}


