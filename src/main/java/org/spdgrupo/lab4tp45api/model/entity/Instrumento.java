package org.spdgrupo.lab4tp45api.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Instrumento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String instrumento;
    private String marca;
    private String modelo;

    @Column(length = 500)
    private String descripcion;
    private String imagen;
    private BigDecimal precio;
    private String costoEnvio;
    private int cantidadVendida;
    private boolean activo;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private CategoriaInstrumento categoria;
}
