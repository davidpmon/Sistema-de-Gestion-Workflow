package com.example.demo.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter //genera los getters automaticamente con lombok
@Setter //genera los setters automaticamente con lombok
@NoArgsConstructor // Genera el constructor vacío (que pide JPA)
@AllArgsConstructor // Genera un constructor con todos los atributos
@Entity
@Table(name = "documentos_usuario")
public class DocumentoUsuario {
    @EmbeddedId
    private DocumentoUsuarioId id;
    @ManyToOne
    @MapsId
    @JoinColumn(name = "id_documento")
    private Documento documento;
    @ManyToOne
    @MapsId
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol_documento", nullable = false)
    private RolDocumento rolDocumento;

}
