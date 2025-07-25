/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.restaurante;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
        String query = "SELECT "
                + "e.idEvento AS idEvento,"
                + "e.nombre AS nombre,"
                + "t.tipo AS tipo"
                + "FROM evento e JOIN tipoEvento t ON e.idTipoEVento = t.idTipoEvento";

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

    public Evento getEventoPorNombre(String nombre) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    boolean crearEvento(String nombre, String tipo, String fecha, String precioVIP, String precioPreferente, String precioGeneral, String precioLaterales) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
