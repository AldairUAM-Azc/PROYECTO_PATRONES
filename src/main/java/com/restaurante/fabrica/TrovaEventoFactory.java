package com.restaurante.fabrica;

import com.restaurante.dao.EventosDAO;
import com.restaurante.dto.PrecioDTO;
import java.time.LocalDate;

/**
 * Fábrica concreta para crear eventos de tipo Trova
 */
public class TrovaEventoFactory implements EventoFactory {
    private EventosDAO eventosDAO;

    public TrovaEventoFactory(EventosDAO eventosDAO) {
        this.eventosDAO = eventosDAO;
    }

    @Override
    public boolean crearEvento(String nombre, String tipo, LocalDate fecha, PrecioDTO precio) {
        return eventosDAO.crearEventoTrova(nombre, tipo, fecha, precio);
    }
}
