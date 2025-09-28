package com.example.demo.repositories;

import com.example.demo.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    // Buscar usuario por cédula
    Optional<Usuario> findByCedula(String cedula);

    // Buscar usuario por cédula y contraseña (no recomendado en producción sin cifrado)
    Optional<Usuario> findByCedulaAndContraseña(String cedula, String contraseña);
}
