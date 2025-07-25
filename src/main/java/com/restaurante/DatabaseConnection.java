package com.restaurante;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Avalos Albino Aldair Oswaldo 2222005685
 */
public class DatabaseConnection {

    private static DatabaseConnection instance;
    private Connection connection;

    public DatabaseConnection() {
        try {
//      connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/reservacion", "root", "Lawbin2328");
//      connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/reservacion", "root", "root");
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:test.db");
            System.out.println("Connected to DB succesfully");
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
            throw new Error("Cannot make connection to the database");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public List<Evento> getAllEventos() throws SQLException {
        List<Evento> eventos = new ArrayList<>();
        String query = "SELECT e.idEvento AS idEvento, e.nombre AS nombre, t.tipo AS tipo FROM evento e JOIN tipoEvento t ON e.idTipoEVento = t.idTipoEvento";

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int idEvento = rs.getInt("idEvento");
                String nombre = rs.getString("nombre");
                String tipo = rs.getString("tipo");
                eventos.add(new Evento(idEvento, nombre, tipo));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        return eventos;
    }

    public List<Evento> getEventosAllFields() throws SQLException {
//  NEED TABLE MODEL INFO (column names and how many)
        List<Evento> eventos = new ArrayList<>();
//    String query = "SELECT * FROM evento";
//
//    try {
//      PreparedStatement ps = connection.prepareStatement(query);
//      ResultSet rs = ps.executeQuery();
//      while (rs.next()) {
//        int idEvento = rs.getInt("idEvento");
//        String nombre = rs.getString("nombre");
//        eventos.add(new Evento(idEvento, nombre, tipo));
//      }
//    } catch (SQLException ex) {
//      Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
//    }
        return eventos;
    }

    public void closeConnection() {
        if (instance != null) {
            try {
                connection.close();
                instance = null; // Set to null so getInstance can re-establish if needed (e.g., for testing)
                System.out.println("Database connection closed successfully.");
            } catch (SQLException ex) {
                System.out.println("Error closing database connection.");
            }
        }
    }

    public Evento getEventoPorNombre(String nombre) throws SQLException {
        String query = "SELECT * FROM evento WHERE nombre = ?";
        Evento evento = null;
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, nombre);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            int idEvento = rs.getInt("idEvento");
            String nombreField = rs.getString("nombre");
            evento = new Evento(idEvento, nombreField);

        }
        return evento;
    }

    public boolean crearEvento(String nombre, String tipo, String fecha, String precioVIP, String precioPreferente, String precioGeneral, String precioLaterales) throws SQLException {
        String query = "CALL evento" + tipo + "( ? , ? , ? , ? , ? , ? , ? );";
        try (CallableStatement stmt = connection.prepareCall(query)) {
            stmt.setString(1, nombre); // Set the IN parameter (1-based index)
            stmt.setString(2, tipo);
            stmt.setString(3, fecha);
            stmt.setFloat(4, Float.parseFloat(precioVIP));
            stmt.setFloat(5, Float.parseFloat(precioPreferente));
            stmt.setFloat(6, Float.parseFloat(precioGeneral));
            stmt.setFloat(7, Float.parseFloat(precioLaterales));

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }

    }

    public PreparedStatement prepareStatement(String query) throws SQLException {
        return connection.prepareStatement(query);
    }

    public CallableStatement prepareCall(String query) throws SQLException {
        return connection.prepareCall(query);
    }

    public Connection getConnection() {
        return connection;
    }

}
