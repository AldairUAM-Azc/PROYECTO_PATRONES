/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.restaurante;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aldair
 */
public class EventosDAO {

  public DatabaseConnection dbConn;

  public EventosDAO() {
    dbConn = DatabaseConnection.getInstance();
  }

  public List<Evento> getAllEventos() throws SQLException {
    List<Evento> eventos = new ArrayList<>();
    String query = """
                    SELECT 
                      e.idEvento AS idEvento,
                      e.nombre AS nombre,
                      t.tipo AS tipo
                    FROM 
                      evento AS e 
                      JOIN tipoEvento AS t ON e.idTipoEVento = t.idTipoEvento
                  """;

    try {
      PreparedStatement ps = dbConn.prepareStatement(query);
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        int idEvento = rs.getInt("idEvento");
        String nombre = rs.getString("nombre");
        String tipo = rs.getString("tipo");
        Evento ev = new Evento();
        ev.setIdEvento(idEvento);
        ev.setNombre(nombre);
        ev.setTipo(tipo);
        eventos.add(ev);
      }
    } catch (SQLException ex) {
      Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
    }
    return eventos;
  }

  public Evento getEventoPorNombre(String targetNombre) throws SQLException {
    Evento evento = new Evento();
    String query = "SELECT * FROM evento WHERE nombre = ?";

    try {
      PreparedStatement ps = dbConn.prepareStatement(query);
      ps.setString(1, targetNombre);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        int idEvento = rs.getInt("idEvento");
        String nombre = rs.getString("nombre");
        int idTipoEvento = rs.getInt("idTipoEvento");
        String descripcion = rs.getString("descripcion");
        Date fecha = rs.getDate("fecha");

        evento.setIdEvento(idEvento);
        evento.setNombre(nombre);
        evento.setIdTipoEvento(idTipoEvento);
        evento.setDescripcion(descripcion);
        evento.setFecha(fecha);
      }
    } catch (SQLException ex) {
      Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
    }
    return evento;
  }

  boolean crearEvento(
          String nombre,
          String tipo,
          Date fecha,
          Double precioVIP,
          Double precioPreferente,
          Double precioGeneral,
          Double precioLaterales) throws SQLException {
    String query = "CALL evento" + tipo + "( ? , ? , ? , ? , ? , ? , ? );";
    CallableStatement ps = dbConn.prepareCall(query);
    ps.setString(1, nombre);
    ps.setString(2, tipo);
    ps.setDate(3, fecha);
    ps.setDouble(4, precioVIP);
    ps.setDouble(5, precioPreferente);
    ps.setDouble(6, precioGeneral);
    ps.setDouble(7, precioLaterales);
    return ps.execute();
  }

  List<Evento> getEventos() throws SQLException {
    List<Evento> eventos = new ArrayList<>();
    String query = "SELECT "
            + "* "
            + "FROM evento";

    try {
      PreparedStatement ps = dbConn.prepareStatement(query);
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        int idEvento = rs.getInt("idEvento");
        String nombre = rs.getString("nombre");
        int idTipoEvento = rs.getInt("idTipoEvento");
        String descripcion = rs.getString("descripcion");
        Date fecha = rs.getDate("fecha");
        Evento ev = new Evento();
        ev.setIdEvento(idEvento);
        ev.setNombre(nombre);
        ev.setIdEvento(idTipoEvento);
        ev.setDescripcion(descripcion);
        ev.setFecha(fecha);
        eventos.add(ev);
      }
    } catch (SQLException ex) {
      Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
    }
    return eventos;
  }

  List<EstadoSillas> getEstadoSillas(int targetIdEvento) throws SQLException {
    List<EstadoSillas> estadoSillasLista = new ArrayList<>();
    String query = """ 
                   SELECT 
                       e.nombre, 
                       m.numero AS Mesa, 
                       s.letra AS Silla, 
                       s.estado , 
                       p.precio 
                   FROM evento e     
                     JOIN precioEvento p ON e.idEvento = p.idEvento    
                     JOIN mesa m ON p.idPrecio = m.idPrecio    
                     JOIN silla s ON m.idMesa = s.idMesa    
                   WHERE e.idEvento =  ?;""";
    try {
      PreparedStatement ps = dbConn.prepareStatement(query);
      ps.setInt(1, targetIdEvento);
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        String eventoNombre = rs.getString("nombre");
        int mesaNumero = rs.getInt("Mesa");
        String sillaLetra = rs.getString("Silla");
        boolean sillaEstado = rs.getBoolean("estadO");
        double precio = rs.getDouble("precio");
        EstadoSillas estadoSillas = new EstadoSillas();
        estadoSillas.setEventoNombre(eventoNombre);
        estadoSillas.setMesa(mesaNumero);
        estadoSillas.setSilla(sillaLetra);
        estadoSillas.setSillaEstado(sillaEstado);
        estadoSillas.setPrecio(precio);
        estadoSillasLista.add(estadoSillas);
      }
      return estadoSillasLista;
    } catch (SQLException ex) {
      Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
    }
    return estadoSillasLista;
  }

  public boolean reservarSillas(int idEvento, int mesaNumero, String sillaLetra) throws SQLException {

    String query = """
                        UPDATE silla s
                            JOIN mesa m ON m.idMesa = s.idMesa
                            JOIN precioEvento p ON p.idPrecio = m.idPrecio
                            JOIN evento e ON e.idEvento = p.idEvento
                        SET s.estado = true
                            WHERE m.numero = ? 
                                AND e.idEvento = ?
                                AND s.letra = ?;
                       """;
    PreparedStatement ps = dbConn.prepareStatement(query);
    ps.setInt(1, mesaNumero);
    ps.setInt(2, idEvento);
    ps.setString(3, sillaLetra);
    return ps.execute();
  }
}
