package com.restaurante;

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

class PrecioDTO {

  private double VIP;
  private double Preferente;
  private double General;
  private double Laterales;

  public double getVIP() {
    return VIP;
  }

  public void setVIP(double VIP) {
    this.VIP = VIP;
  }

  public double getPreferente() {
    return Preferente;
  }

  public void setPreferente(double Preferente) {
    this.Preferente = Preferente;
  }

  public double getGeneral() {
    return General;
  }

  public void setGeneral(double General) {
    this.General = General;
  }

  public double getLaterales() {
    return Laterales;
  }

  public void setLaterales(double Laterales) {
    this.Laterales = Laterales;
  }

  @Override
  public String toString() {
    return "PrecioDTO{" + "VIP=" + VIP + ", Preferente=" + Preferente + ", General=" + General + ", Laterales=" + Laterales + '}';
  }

}
