/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.restaurante.adminagendareventos;

import com.restaurante.Evento;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aldair
 */
public class GestorEventos {

    public static List<Evento> eventosAgendados;

    public GestorEventos() {
        eventosAgendados = new ArrayList<>();
    }

    public void agregarEvento(Evento evento) {
        eventosAgendados.add(evento);
    }

    public List<Evento> getEventosAgendados() {
        return eventosAgendados;
    }

}
