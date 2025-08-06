package com.restaurante;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import java.sql.Date;
import java.sql.SQLException;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDate;
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
    int idEvento = Integer.parseInt(ctx.pathParam("idEvento"));
    try {
      List<EstadoSillas> estadoSillas = eventosDAO.getEstadoSillas(idEvento);
      ctx.json(estadoSillas);
    } catch (SQLException ex) {
      ctx.status(500).result("Error interno del servidor al obtener el listado de eventos");
    }
  }

  public static void crearEvento(Context ctx) {
    EventoDTO dto = ctx.bodyAsClass(EventoDTO.class);
    System.out.println(dto);
    String nombre = dto.getNombre();
    String tipo = dto.getTipo();
    LocalDate fecha = dto.getFecha();
    PrecioDTO precio = dto.getPrecio();

    if (tipo.equals("Trova")) {
      boolean eventoCreado = eventosDAO.crearEventoTrova(nombre, tipo, fecha, precio);
      if (!eventoCreado) {
        ctx.status(500).result("Error en el servidor");
      }
      ctx.result("Evento de " + nombre + " agregado");
    }

    if (tipo.equals("General")) {
      boolean eventoCreado = eventosDAO.crearEventoGeneral(nombre, tipo, fecha, precio);
      if (!eventoCreado) {
        ctx.status(500).result("Error en el servidor");
      }
      ctx.result("Evento de " + nombre + " agregado");
    }
  }

  public static void sembrado(Context ctx) {
    String eventDataJsonString = ctx.formParam("data");
    if (eventDataJsonString != null && !eventDataJsonString.isEmpty()) {
      ObjectMapper objectMapper = new ObjectMapper();

      try {
        EventData eventData = objectMapper.readValue(eventDataJsonString, EventData.class);
        String viewName = "views/" + eventData.getTipo() + ".html";
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

    int idEvento = Integer.parseInt(ctx.pathParam("idEvento"));
    ReservacionDTO reservacion = ctx.bodyAsClass(ReservacionDTO.class);
    int codigo = reservacion.getCodigo();
    int mesa = reservacion.getSilla().getMesa();
    String silla = reservacion.getSilla().getSilla();
    if (silla.isBlank()) {
      ctx.status(400).json(Map.of("error", "Los parámetros mesa y silla deben ser números válidos"));
    }

    boolean isReservacion = eventosDAO.reservarSillas(codigo, mesa, idEvento, silla);
    if (!isReservacion) {
      ctx.status(400).json(Map.of("message", "No se encontró el registro con los datos proporcionados"));
    }
    ctx.status(200).json(Map.of("message", "Reserva realizada correctamente"));

  }

  public static void getPrecios(Context ctx) {
    int idEvento = Integer.parseInt(ctx.pathParam("idEvento"));
    List<TipoPrecioDTO> lista = eventosDAO.getPrecios(idEvento);
    ctx.json(lista);
  }

  public static void getDatos(Context ctx) {
    try {
      String jsonDataString = ctx.formParam("jsonData");
      ObjectMapper objectMapper = new ObjectMapper();
      DatosDTO datos = objectMapper.readValue(jsonDataString, DatosDTO.class);
      Map<String, Object> modelo = new HashMap<>();
      modelo.put("sembrado", datos.getSembrado());
      modelo.put("tipo", datos.getTipo());
      modelo.put("listaMesaSilla", datos.getListaMesaSilla());
      ctx.render("views/datos.html", modelo);
    } catch (JsonProcessingException ex) {
      Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  public static void getConteo(Context ctx) {
    int idEvento = Integer.parseInt(ctx.pathParam("idEvento"));
    int conteo = eventosDAO.obtenerConteo(idEvento);
    ctx.json(Map.of("conteo", conteo));
  }

  public static void postCodigo(Context ctx) {
    ClienteDTO dto = ctx.bodyAsClass(ClienteDTO.class);

    if (Double.isNaN(dto.getCodigo()) || dto.getNombreCompleto().isBlank() || dto.getTelefono().isBlank()) {
      ctx.status(400).json(Map.of("error", "Parametros invalidos"));
    }

    boolean isCodigoInserted = eventosDAO.insertarReserva(dto.getCodigo(), dto.getNombreCompleto(), dto.getTelefono());
    if (!isCodigoInserted) {
      ctx.status(404).json(Map.of("message", "No se encontró el registro con los datos proporcionados"));
    }
    ctx.status(200).json(Map.of("message", "Reserva realizada correctamente"));
  }
}
