package com.restaurante;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurante.adminagendareventos.GestorEventos;
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
    public static GestorEventos gestorEventos = new GestorEventos();
    
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
        int idEvento = -1;
        try {
            idEvento = Integer.parseInt(ctx.pathParam("idEvento"));
        } catch (NumberFormatException ex) {
            ctx.status(400).result("No event id like that");
        }
        
        try {
            List<EstadoSillas> estadoSillas = eventosDAO.getEstadoSillas(idEvento);
            ctx.json(estadoSillas);
        } catch (SQLException ex) {
            ctx.status(500).result("Error interno del servidor al obtener el listado de eventos");
        }
    }
    
    public static void crearEvento(Context ctx) {
        String nombre = ctx.formParam("nombre");
        String tipo = ctx.formParam("tipo");
        Date fecha = null;
        Double precioVIP = -1.0;
        Double precioPreferente = -1.0;
        Double precioGeneral = -1.0;
        Double precioLaterales = -1.0;
        
        try {
            precioVIP = Double.valueOf(ctx.formParam("precio.VIP"));
            precioPreferente = Double.valueOf(ctx.formParam("precio.Preferente"));
            precioGeneral = Double.valueOf(ctx.formParam("precio.General"));
            precioLaterales = Double.valueOf(ctx.formParam("precio.Laterales"));
            if (precioVIP < 1.0
                    || precioPreferente < 1.0
                    || precioGeneral < 1.0
                    || precioLaterales < 1.0) {
                throw new NumberFormatException();
            }
            LocalDate fechaDB = LocalDate.parse(ctx.pathParam("fecha"));
            fecha = Date.valueOf(fechaDB);
            if (fecha.before(Date.from(Instant.now()))) {
                throw new DateTimeException("Can't create an event for past days");
            }
        } catch (NumberFormatException ex) {
            ctx.status(400).result("Couldnt create an event with the given parameters.");
        } catch (DateTimeException ex) {
            ctx.status(400).result("Could'nt create an event with the given parameters.");
        }
        
        try {
            boolean isEventCreated = eventosDAO.crearEvento(
                    nombre,
                    tipo,
                    fecha,
                    precioVIP,
                    precioPreferente,
                    precioGeneral,
                    precioLaterales);
            if (isEventCreated) {
                ctx.result("Evento agregado ID:");
            } else {
                ctx.result("Evento no agregado");
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(Server.class
                    .getName()).log(Level.SEVERE, null, ex);
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
        int idEvento = Integer.parseInt(ctx.pathParam("idEvento"));
        ReservacionJSON reservacion = ctx.bodyAsClass(ReservacionJSON.class
        );
        
        try {
            boolean isReservacion = eventosDAO.reservarSillas(idEvento, reservacion.getMesa(), reservacion.getSilla());
            if (isReservacion) {
                ctx.result("Reservacion confirmada.");
            } else {
                ctx.result("No se pudo realizar la reservacion");
            }
        } catch (SQLException ex) {
            ctx.status(500).result("Couldn't make reservation");
        }
    }
    
    public static void mostrarFormularioAgendarEvento(Context ctx) {
        Map<String, Object> modelo = new HashMap<>();
        modelo.put("eventosAgendados", gestorEventos.getEventosAgendados());
        ctx.render("templates/admin-agendar-evento.html", modelo);
    }
    
    public static void agendarEvento(Context ctx) {
        ctx.result("RIGHT HERE");
        System.out.println(ctx);

//        EventoDTO eventoData = ctx.bodyAsClass(EventoDTO.class);
        // Lógica para seleccionar la fábrica y agendar el evento
//        FabricaAbstractaEvento fabrica;
//        if ("baile".equalsIgnoreCase(eventoData.tipo)) {
//            fabrica = new FabricaEventoBaile();
//        } else if ("trova".equalsIgnoreCase(eventoData.tipo)) {
//            fabrica = new FabricaEventoTrova();
//        } else {
//            fabrica = new FabricaEventoGeneral();
//        }
//        Evento evento = fabrica.crearEvento();
//        gestorEventos.agregarEvento(evento);
//        ctx.redirect("/admin/agendarEvento");
// GestorEventos tiene un coleccion de Evento
// tiene un metodo agendarEvento(Evento evento) o, tal vez,
// agendarEvento(FabricaAbstractaEvento fabrica);
// en la implementacion del metodo se crea el evento y se guarda en la base de datos usando el EventoDAO, responde con un boolean para confirmar la reserva.          
    }
}
