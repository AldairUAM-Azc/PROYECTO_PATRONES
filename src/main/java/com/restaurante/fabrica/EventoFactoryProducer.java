package com.restaurante.fabrica;

import com.restaurante.dao.EventosDAO;

/**
 * Clase productora que determina qué tipo de fábrica crear según el tipo de evento
 */
public class EventoFactoryProducer {
    private EventosDAO eventosDAO;

    public EventoFactoryProducer(EventosDAO eventosDAO) {
        this.eventosDAO = eventosDAO;
    }

    public EventoFactory getFactory(String tipo) {
        if (tipo.equals("Trova")) {
            return new TrovaEventoFactory(eventosDAO);
        } else if (tipo.equals("General")) {
            return new GeneralEventoFactory(eventosDAO);
        }
        throw new IllegalArgumentException("Tipo de evento no soportado: " + tipo);
    }
}
