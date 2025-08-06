package com.restaurante.dto;

import java.time.LocalDate;

/**
 *
 * @author Avalos Albino Aldair Oswaldo 2222005685
 */
public class EventoDTO {

  private String nombre;
  private LocalDate fecha;
  private String tipo;
  private PrecioDTO precio;

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public LocalDate getFecha() {
    return fecha;
  }

  public void setFecha(LocalDate fecha) {
    this.fecha = fecha;
  }

  public String getTipo() {
    return tipo;
  }

  public void setTipo(String tipo) {
    this.tipo = tipo;
  }

  public PrecioDTO getPrecio() {
    return precio;
  }

  public void setPrecio(PrecioDTO precio) {
    this.precio = precio;
  }

  @Override
  public String toString() {
    return "EventoDTO{" + "nombre=" + nombre + ", fecha=" + fecha + ", tipo=" + tipo + ", precio=" + precio + '}';
  }

}
