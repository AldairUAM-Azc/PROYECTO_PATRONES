package com.restaurante;

/**
 *
 * @author Avalos Albino Aldair Oswaldo 2222005685
 */
public class ReservacionConBoletosDTO {

  private String nombre;
  private String telefono;
  private int codigo;
  private int boletos;

  public ReservacionConBoletosDTO(String nombre, String telefono, int codigo, int boletos) {
    this.nombre = nombre;
    this.telefono = telefono;
    this.codigo = codigo;
    this.boletos = boletos;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getTelefono() {
    return telefono;
  }

  public void setTelefono(String telefono) {
    this.telefono = telefono;
  }

  public int getCodigo() {
    return codigo;
  }

  public void setCodigo(int codigo) {
    this.codigo = codigo;
  }

  public int getBoletos() {
    return boletos;
  }

  public void setBoletos(int boletos) {
    this.boletos = boletos;
  }

  @Override
  public String toString() {
    return "ReservacionConBoletosDTO{" + "nombre=" + nombre + ", telefono=" + telefono + ", codigo=" + codigo + ", boletos=" + boletos + '}';
  }

}
