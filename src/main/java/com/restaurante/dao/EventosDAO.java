package com.restaurante.dao;

import com.restaurante.EstadoSillas;
import com.restaurante.Evento;
import com.restaurante.Server;
import com.restaurante.dto.TipoPrecioDTO;
import com.restaurante.dto.PrecioDTO;
import com.restaurante.dto.ClienteDTO;
import com.restaurante.dto.EditarEventoDTO;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
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
            t.tipo AS tipo,
            e.fecha AS fecha
          FROM
            evento AS e
            JOIN tipoEvento AS t ON e.idTipoEVento = t.idTipoEvento
          ORDER BY e.fecha ASC;
        """;

    try {
      PreparedStatement ps = dbConn.prepareStatement(query);
      ResultSet rs = ps.executeQuery();
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy", new Locale("es", "ES"));

      while (rs.next()) {
        int idEvento = rs.getInt("idEvento");
        String nombre = rs.getString("nombre");
        String tipo = rs.getString("tipo");
        LocalDate fechaCruda = rs.getDate("fecha").toLocalDate();
        String fechaFormateada = fechaCruda.format(formatter);
        Evento ev = new Evento();
        ev.setIdEvento(idEvento);
        ev.setNombre(nombre);
        ev.setTipo(tipo);
        ev.setFecha(fechaFormateada);
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
        evento.setFechaDate(fecha);
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

  public List<Evento> getEventos() throws SQLException {
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
        ev.setFechaDate(fecha);
        eventos.add(ev);
      }
    } catch (SQLException ex) {
      Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
    }
    return eventos;
  }

  public List<EstadoSillas> getEstadoSillas(int targetIdEvento) throws SQLException {
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
        estadoSillas.setNombre(eventoNombre);
        estadoSillas.setMesa(mesaNumero);
        estadoSillas.setSilla(sillaLetra);
        estadoSillas.setEstado(sillaEstado);
        estadoSillas.setPrecio(precio);
        estadoSillasLista.add(estadoSillas);
      }
      return estadoSillasLista;
    } catch (SQLException ex) {
      Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
    }
    return estadoSillasLista;
  }

  public boolean reservarSillas(int codigo, int mesaNumero, int idEvento, String sillaLetra) {
    String query = """
           UPDATE silla s
               JOIN mesa m ON m.idMesa = s.idMesa
               JOIN precioEvento p ON p.idPrecio = m.idPrecio
               JOIN evento e ON e.idEvento = p.idEvento
           SET s.estado = true,
               s.codigo = ?
           WHERE m.numero = ?
                 AND e.idEvento = ?
                 AND s.letra = ?;
        """;
    PreparedStatement ps;
    int rows = 0;
    try {
      ps = dbConn.prepareStatement(query);
      ps.setInt(1, codigo);
      ps.setInt(2, mesaNumero);
      ps.setInt(3, idEvento);
      ps.setString(4, sillaLetra);
      rows = ps.executeUpdate();
    } catch (SQLException ex) {
      Logger.getLogger(EventosDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return rows > 0;
  }

  public List<TipoPrecioDTO> getPrecios(int idEvento) {
    List<TipoPrecioDTO> lista = new ArrayList<>();
    String query = """
        SELECT
          t.tipo,
          p.precio
        FROM precioEvento p
             JOIN tipoMesa t ON t.idTipoMesa = p.idTipoMesa
             JOIN evento e ON p.idEvento = e.idEvento
        WHERE e.idEvento = ?;
        """;
    PreparedStatement ps;
    try {
      ps = dbConn.prepareStatement(query);
      ps.setInt(1, idEvento);
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        String tipo = rs.getString("tipo");
        double precio = rs.getDouble("precio");
        TipoPrecioDTO dto = new TipoPrecioDTO();
        dto.setPrecio(precio);
        dto.setTipo(tipo);
        lista.add(dto);
      }

    } catch (SQLException ex) {
      Logger.getLogger(EventosDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return lista;
  }

  public int obtenerConteo(int idEvento) {
    String query = """
        SELECT
          COUNT(*) AS numero
        FROM silla s
          JOIN mesa m ON m.idMesa = s.idMesa
          JOIN precioEvento p ON p.idPrecio = m.idPrecio
          JOIN evento e ON e.idEvento = p.idEvento
        WHERE
          estado = true AND e.idEvento = ?
         """;
    PreparedStatement ps;
    int cuenta = 0;
    try {
      ps = dbConn.prepareStatement(query);
      ps.setInt(1, idEvento);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        cuenta = rs.getInt("numero");
      }
    } catch (SQLException ex) {
      Logger.getLogger(EventosDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return cuenta;
  }

  public boolean insertarReserva(int codigo, String nombreCompleto, String telefono) {
    String query = "INSERT INTO Reserva VALUES( ? , ? , ?)";
    int rows = 0;
    try (PreparedStatement ps = dbConn.prepareStatement(query);) {
      ps.setInt(1, codigo);
      ps.setString(2, nombreCompleto);
      ps.setString(3, telefono);
      rows = ps.executeUpdate();
    } catch (SQLException ex) {
      Logger.getLogger(EventosDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return rows > 0;
  }

  public boolean crearEventoTrova(String nombre, String tipo, LocalDate fecha, PrecioDTO precio) {
    String query = "CALL eventoTrova ( ? , ? , ? , ? , ? , ? , ? );";
    boolean hadResult = false;
    try {
      CallableStatement cs = dbConn.prepareCall(query);
      cs.setString(1, nombre);
      cs.setString(2, tipo);
      cs.setDate(3, Date.valueOf(fecha));
      cs.setDouble(4, precio.getVIP());
      cs.setDouble(5, precio.getPreferente());
      cs.setDouble(6, precio.getGeneral());
      cs.setDouble(7, precio.getLaterales());
      hadResult = cs.execute();
    } catch (SQLException ex) {
      Logger.getLogger(EventosDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return hadResult;
  }

  public boolean crearEventoGeneral(String nombre, String tipo, LocalDate fecha, PrecioDTO precio) {
    String query = "CALL eventoGeneral ( ? , ? , ? , ? , ? , ? );";
    boolean hadResult = false;
    try {
      CallableStatement cs = dbConn.prepareCall(query);
      cs.setString(1, nombre);
      cs.setString(2, tipo);
      cs.setDate(3, Date.valueOf(fecha));
      cs.setDouble(4, precio.getVIP());
      cs.setDouble(5, precio.getPreferente());
      cs.setDouble(6, precio.getGeneral());
      hadResult = cs.execute();
    } catch (SQLException ex) {
      Logger.getLogger(EventosDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return hadResult;
  }

  public boolean eliminarEvento(int idEvento) {
    String query = "DELETE FROM evento WHERE idEvento = ?";
    int rows = 0;
    PreparedStatement ps;
    try {
      ps = dbConn.prepareStatement(query);
      ps.setInt(1, idEvento);
      rows = ps.executeUpdate();
    } catch (SQLException ex) {
      Logger.getLogger(EventosDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return rows > 0;
  }

  public List<EditarEventoDTO> mostrarFormularioEditar(int idEvento) {
    List<EditarEventoDTO> lista = new ArrayList<>();
    String query = """
          SELECT
            m.tipo,
            p.precio,
            e.fecha,
            e.nombre,
            t.tipo AS tipoEvento
          FROM evento e
            JOIN tipoEvento t ON e.idTipoEvento = t.idTipoEvento
            JOIN precioEvento p ON e.idEvento = p.idEvento
            JOIN tipoMesa m ON m.idTipoMesa = p.idTipoMesa
          WHERE e.idEvento = ?;
        """;
    PreparedStatement ps;

    try {
      ps = dbConn.prepareStatement(query);
      ps.setInt(1, idEvento);
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        double precio = rs.getDouble("precio");
        Date fecha = rs.getDate("fecha");
        String nombre = rs.getString("nombre");
        String tipoEvento = rs.getString("tipoEvento");
        lista.add(new EditarEventoDTO(nombre, fecha.toString(), precio, tipoEvento));
      }
    } catch (SQLException ex) {
      Logger.getLogger(EventosDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return lista;
  }

  public boolean actualizarEvento(String nombre, LocalDate fecha, int idEvento) {
    String query = """
         UPDATE evento
         SET nombre = ?, fecha = ?
         WHERE idEvento = ?
        """;
    int rows = 0;
    try {
      PreparedStatement ps = dbConn.prepareStatement(query);
      ps.setString(1, nombre);
      ps.setDate(2, Date.valueOf(fecha));
      ps.setInt(3, idEvento);
      rows = ps.executeUpdate();
    } catch (SQLException ex) {
      Logger.getLogger(EventosDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return rows > 0;
  }

  public boolean actualizarPreciosTrova(double vip, double preferente, double general, double laterales, int idEvento) {
    String query = """
        UPDATE precioEvento
        SET precio =
            CASE
              WHEN idTipoMesa = 1 THEN ?
              WHEN idTipoMesa = 2 THEN ?
              WHEN idTipoMesa = 3 THEN ?
              WHEN idTipoMesa = 4 THEN ?
            END
        WHERE idEvento = ?
        """;
    PreparedStatement ps;
    int rows = 0;
    try {
      ps = dbConn.prepareStatement(query);
      ps.setDouble(1, vip);
      ps.setDouble(2, preferente);
      ps.setDouble(3, general);
      ps.setDouble(4, laterales);
      ps.setInt(5, idEvento);
      rows = ps.executeUpdate();
    } catch (SQLException ex) {
      Logger.getLogger(EventosDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return rows > 0;
  }

  public boolean actualizarPreciosGeneral(double vip, double preferente, double general, int idEvento) {
    String query = """
        UPDATE precioEvento
        SET precio =
            CASE
              WHEN idTipoMesa = 1 THEN ?
              WHEN idTipoMesa = 2 THEN ?
              WHEN idTipoMesa = 3 THEN ?
            END
        WHERE idEvento = ?
        """;
    PreparedStatement ps;
    int rows = 0;
    try {
      ps = dbConn.prepareStatement(query);
      ps.setDouble(1, vip);
      ps.setDouble(2, preferente);
      ps.setDouble(3, general);
      ps.setInt(4, idEvento);
      rows = ps.executeUpdate();
    } catch (SQLException ex) {
      Logger.getLogger(EventosDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return rows > 0;
  }

  public List<ClienteDTO> listarReservas(int idEvento) {
    List<ClienteDTO> lista = new ArrayList<>();
    String query = """
         SELECT DISTINCT
           r.nombre,
           r.telefono,
           r.codigo
         FROM reserva r
           JOIN silla s ON s.codigo = r.codigo
           JOIN mesa m ON m.idMesa = s.idMesa
           JOIN precioEvento p ON p.idPrecio = m.idPrecio
           JOIN evento e ON e.idEvento = p.idEvento
         WHERE e.idEvento = ?;
        """;
    PreparedStatement ps;
    try {
      ps = dbConn.prepareStatement(query);
      ps.setInt(1, idEvento);
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        String nombre = rs.getString("nombre");
        String telefono = rs.getString("telefono");
        int codigo = rs.getInt("codigo");
        lista.add(new ClienteDTO(codigo, nombre, telefono));
      }
    } catch (SQLException ex) {
      Logger.getLogger(EventosDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return lista;
  }

  public String getNombreEvento(int idEvento) {
    String query = "SELECT nombre FROM evento WHERE idEvento = ?";
    PreparedStatement ps;
    String nombre = "";
    try {
      ps = dbConn.prepareStatement(query);
      ps.setInt(1, idEvento);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        nombre = rs.getString("nombre");
      }
    } catch (SQLException ex) {
      Logger.getLogger(EventosDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return nombre;
  }

  public int countBoletosForReserva(int idEvento, int codigoReserva) {
    String queryConteo = """
        SELECT
          COUNT(*) AS boletos
        FROM reserva r
          JOIN silla s ON r.codigo = s.codigo
          JOIN mesa m ON m.idMesa = s.idMesa
          JOIN precioEvento p ON p.idPrecio = m.idPrecio
          JOIN evento e ON e.idEvento = p.idEvento
        WHERE
          e.idEvento = ?
          AND r.codigo = ?;
                    """;

    PreparedStatement ps;
    try {
      ps = dbConn.prepareStatement(queryConteo);
      ps.setInt(1, idEvento);
      ps.setInt(2, codigoReserva);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        return rs.getInt("boletos");
      }
    } catch (SQLException ex) {
      Logger.getLogger(EventosDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return 0; // Si no se encuentra o hay error, retorna 0 boletos
  }
}
