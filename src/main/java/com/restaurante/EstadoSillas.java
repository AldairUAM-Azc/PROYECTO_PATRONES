/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.restaurante;

/**
 *
 * @author aldair
 */
public class EstadoSillas {

  private String eventoNombre;
  private int Mesa;
  private String Silla;
  private boolean estado;
  private double precio;

  public EstadoSillas() {
  }

  public String getNombre() {
    return eventoNombre;
  }

  public void setNombre(String eventoNombre) {
    this.eventoNombre = eventoNombre;
  }

  public int getMesa() {
    return Mesa;
  }

  public void setMesa(int mesaNumero) {
    this.Mesa = mesaNumero;
  }

  public String getSilla() {
    return Silla;
  }

  public void setSilla(String sillaLetra) {
    this.Silla = sillaLetra;
  }

  public boolean isEstado() {
    return estado;
  }

  public void setEstado(boolean sillaEstado) {
    this.estado = sillaEstado;
  }

  public double getPrecio() {
    return precio;
  }

  public void setPrecio(double precio) {
    this.precio = precio;
  }

  @Override
  public String toString() {
    return "EstadoSillas{" + "eventoNombre=" + eventoNombre + ", mesaNumero=" + Mesa + ", sillaLetra=" + Silla + ", sillaEstado=" + estado + ", precio=" + precio + '}';
  }

}
