package com.restaurante;

import java.util.List;

/**
 *
 * @author Avalos Albino Aldair Oswaldo 2222005685
 */
public class EditarEventoDTO {

  private String nombreEvento;
  private String fechaEvento;
  private double precio;
  private String tipoEvento;

  public EditarEventoDTO(String nombreEvento, String fechaEvento, double precio, String tipoEvento) {
    this.nombreEvento = nombreEvento;
    this.fechaEvento = fechaEvento;
    this.precio = precio;
    this.tipoEvento = tipoEvento;
  }

  public String getNombre() {
    return nombreEvento;
  }

  public void setNombre(String nombreEvento) {
    this.nombreEvento = nombreEvento;
  }

  public String getFecha() {
    return fechaEvento;
  }

  public void setFecha(String fechaEvento) {
    this.fechaEvento = fechaEvento;
  }

  public double getPrecio() {
    return precio;
  }

  public void setPrecio(double precio) {
    this.precio = precio;
  }

  public String getTipoEvento() {
    return tipoEvento;
  }

  public void setTipoEvento(String tipoEvento) {
    this.tipoEvento = tipoEvento;
  }

}

