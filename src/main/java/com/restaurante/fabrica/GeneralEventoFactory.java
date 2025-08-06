package com.restaurante.fabrica;

import com.restaurante.dao.EventosDAO;
import com.restaurante.dto.PrecioDTO;
import java.time.LocalDate;

/**
 * Fábrica concreta para crear eventos de tipo General
 */
public class GeneralEventoFactory implements EventoFactory {
    private EventosDAO eventosDAO;

    public GeneralEventoFactory(EventosDAO eventosDAO) {
        this.eventosDAO = eventosDAO;
    }

    @Override
    public boolean crearEvento(String nombre, String tipo, LocalDate fecha, PrecioDTO precio) {
        return eventosDAO.crearEventoGeneral(nombre, tipo, fecha, precio);
    }
}
