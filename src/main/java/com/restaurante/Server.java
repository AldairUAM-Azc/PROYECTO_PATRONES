package com.restaurante;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Avalos Albino Aldair Oswaldo 2222005685
 */
public class Server {

  public static EventosDAO eventosDAO = new EventosDAO();

  public Server() {
  }

  public static void listadoDeEventos(Context ctx) {
    try {
      List<Evento> eventos = eventosDAO.getAllEventos();
      ctx.json(eventos);
    } catch (SQLException ex) {
      ctx.status(500).result("Error interno del servidor al obtener el listado de eventos");
    }
  }

  public static void evento(Context ctx) {
    try {
      List<Evento> eventos = eventosDAO.getEventos();
      ctx.json(eventos);
    } catch (SQLException ex) {
      ctx.status(500).result("Error interno del servidor al obtener el evento");
    }
    ctx.result("NO IMPLEMENTADO");
  }

  public static void eventoPorNombre(Context ctx) {
    String nombre = ctx.pathParam("nombre");

    try {
      Evento evento = eventosDAO.getEventoPorNombre(nombre);
      if (evento == null) {
        ctx.status(400).result("Evento no encontrado");
      } else {
        ctx.json(evento);
      }
    } catch (SQLException ex) {
      Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
      ctx.status(500).result("Error interno del servidor al consultar el evento");
    }
  }

  public static void estadoSillas(Context ctx) {
    // this def incorrect, i need to check javalin doc toknow how to get requests params
//        int idEvento = ctx.body().codePointAt(0);
    int idEvento = Integer.parseInt(ctx.req().getParameter("idEvento"));
    try {
      List<EstadoSillas> estadoSillas = eventosDAO.getEstadoSillas(idEvento);
      ctx.json(estadoSillas);
    } catch (SQLException ex) {
      ctx.status(500).result("Error interno del servidor al obtener el listado de eventos");
    }
  }

  public static void crearEvento(Context ctx) {
    String nombre = ctx.formParam("nombre");
    String fecha = ctx.formParam("fecha");
    String tipo = ctx.formParam("tipo");
    String precioVIP = ctx.formParam("precio.VIP");
    String precioPreferente = ctx.formParam("precio.Preferente");
    String precioGeneral = ctx.formParam("precio.General");
    String precioLaterales = ctx.formParam("precio.Laterales");
    try {
      if (eventosDAO.crearEvento(nombre, tipo, fecha, precioVIP, precioPreferente, precioGeneral, precioLaterales)) {
        ctx.result("Evento agregado ID:");
      } else {
        ctx.result("Evento no agregado");
      }
    } catch (SQLException ex) {
      Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
      ctx.status(500).result("Error en el servidor");
    }
  }

  public static void sembrado(Context ctx) {
    String eventDataJsonString = ctx.formParam("data");
    if (eventDataJsonString != null && !eventDataJsonString.isEmpty()) {
      ObjectMapper objectMapper = new ObjectMapper();
      try {
        EventData eventData = objectMapper.readValue(eventDataJsonString, EventData.class);
        System.out.println(eventData.getIdEvento());
        System.out.println(eventData.getNombre());
        System.out.println(eventData.getTipo());

        String viewName = "templates/" + eventData.getTipo() + ".html";
        Map<String, Object> model = new HashMap<>();
        model.put("evento", eventData);
        ctx.render(viewName, model);
      } catch (JsonProcessingException e) {
        ctx.status(500).result("Error parsing JSON: " + e.getMessage());
      }
    } else {
      ctx.status(400).result("Data parameter is missing");
    }
  }

  public static void reservarSillas(Context ctx) {
    throw new UnsupportedOperationException("Not implemented yet");
  }
}
