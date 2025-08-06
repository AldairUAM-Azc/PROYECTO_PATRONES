package com.restaurante;

/**
 *
 * @author Avalos Albino Aldair Oswaldo 2222005685
 */
public class ClienteDTO {

  private int codigo;
  private String nombreCompleto;
  private String telefono;

  public int getCodigo() {
    return codigo;
  }

  public void setCodigo(int codigo) {
    this.codigo = codigo;
  }

  public String getNombreCompleto() {
    return nombreCompleto;
  }

  public void setNombreCompleto(String nombreCompleto) {
    this.nombreCompleto = nombreCompleto;
  }

  public String getTelefono() {
    return telefono;
  }

  public void setTelefono(String telefono) {
    this.telefono = telefono;
  }

  @Override
  public String toString() {
    return "ClienteDTO{" + "codigo=" + codigo + ", nombreCompleto=" + nombreCompleto + ", telefono=" + telefono + '}';
  }

}
