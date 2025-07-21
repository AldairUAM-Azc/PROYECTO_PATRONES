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

    public static DatabaseConnection dbConn = DatabaseConnection.getInstance();

    public static void listadoDeEventos(Context ctx) {
        try {
            List<Evento> eventos = dbConn.getAllEventos();
            ctx.json(eventos);
        } catch (SQLException ex) {
            ctx.status(500).result("Error interno del servidor al obtener el listado de eventos");
        }
    }

    public static void evento(Context ctx) {
//    try {
//      Evento evento = dbConn.getEventosAllFields();
//      ctx.json(evento);
//    } catch (SQLException ex) {
//      ctx.status(500).result("Error interno del servidor al obtener el evento");
//    }
        ctx.result("NO IMPLEMENTADO");
    }

    public static void eventoPorNombre(Context ctx) {
        String nombre = ctx.pathParam("nombre");

        try {
            Evento evento = dbConn.getEventoPorNombre(nombre);
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
//        System.out.println(ctx.body());
//        dbConn.estadoSillas();
//        String query = """ 
//                   SELECT e.nombre, m.numero AS Mesa, s.letra AS Silla, s.estado , p.precio 
//                   FROM evento e     
//                     JOIN precioEvento p ON e.idEvento = p.idEvento    
//                     JOIN mesa m ON p.idPrecio = m.idPrecio    
//                     JOIN silla s ON m.idMesa = s.idMesa    
//                   WHERE e    .idEvento =  ?;""";
//        ctx.result("sillas " + idEvento);
//        throw new UnsupportedOperationException("Not implemented yet");
    }

    public static void crearEvento(Context ctx) {
        String nombre = ctx.formParam("nombre");
        String fecha = ctx.formParam("fecha");
        String tipo = ctx.formParam("tipo");
        String precioVIP = ctx.formParam("precio");
        String precioPreferente = ctx.formParam("precio");
        String precioGeneral = ctx.formParam("precio");
        String precioLaterales = ctx.formParam("precio");
        try {
            if (dbConn.crearEvento(nombre, tipo, fecha, precioVIP, precioPreferente, precioGeneral, precioLaterales)) {
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
        String jsonDataString = ctx.formParam("data");
        ObjectMapper mapper = new ObjectMapper();
        EventData eventData = null;
        try {
            eventData = mapper.readValue(jsonDataString, EventData.class);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            ctx.status(500).result("Error interno del servidor");
        }

        String viewName = "templates/" + eventData.getTipo() + ".html";
        Map<String, Object> model = new HashMap<>();
        model.put("evento", eventData);
        ctx.render(viewName, model);
    }

    public static void reservarSillas(Context ctx) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
