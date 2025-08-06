package com.restaurante;

import java.util.Date;

/**
 *
 * @author Avalos Albino Aldair Oswaldo 2222005685
 */
public class Evento {

  private int idEvento;
  private String nombre;
  private int idTipoEvento;
  private String tipo;
  private String descripcion;
  private Date fecha;
  private String fechaFormateada;

  public Evento() {
  }

  public Evento(int idEvento, String nombre) {
    this.idEvento = idEvento;
    this.nombre = nombre;
  }

  public Evento(int idEvento, String nombre, String tipo) {
    this.idEvento = idEvento;
    this.nombre = nombre;
    this.tipo = tipo;
  }

  public int getIdEvento() {
    return idEvento;
  }

  public String getNombre() {
    return nombre;
  }

  public String getTipo() {
    return tipo;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public Date getFechaDate() {
    return fecha;
  }

  public void setIdEvento(int idEvento) {
    this.idEvento = idEvento;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public void setTipo(String tipo) {
    this.tipo = tipo;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public void setFechaDate(Date fecha) {
    this.fecha = fecha;
  }

  public int getIdTipoEvento() {
    return idTipoEvento;
  }

  public void setIdTipoEvento(int idTipoEvento) {
    this.idTipoEvento = idTipoEvento;
  }

  public String getFecha() {
    return fechaFormateada;
  }

  public void setFecha(String fechaFormateada) {
    this.fechaFormateada = fechaFormateada;
  }

}
