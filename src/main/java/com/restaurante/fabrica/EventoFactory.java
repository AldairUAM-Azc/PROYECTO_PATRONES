package com.restaurante.fabrica;

import com.restaurante.dto.PrecioDTO;
import java.time.LocalDate;

/**
 * Interfaz base para la fábrica de eventos
 */
public interface EventoFactory {
    boolean crearEvento(String nombre, String tipo, LocalDate fecha, PrecioDTO precio);
}
