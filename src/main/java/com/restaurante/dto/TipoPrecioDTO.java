package com.restaurante.dto;

/**
 *
 * @author Avalos Albino Aldair Oswaldo 2222005685
 */
public class TipoPrecioDTO {

  private String tipo;
  private double precio;

  public TipoPrecioDTO() {
  }

  public TipoPrecioDTO(String tipo, double precio) {
    this.tipo = tipo;
    this.precio = precio;
  }

  public String getTipo() {
    return tipo;
  }

  public void setTipo(String tipo) {
    this.tipo = tipo;
  }

  public double getPrecio() {
    return precio;
  }

  public void setPrecio(double precio) {
    this.precio = precio;
  }

}
