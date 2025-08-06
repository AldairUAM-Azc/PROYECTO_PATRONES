package com.restaurante.dto;

/**
 *
 * @author Avalos Albino Aldair Oswaldo 2222005685
 */
public class ReservacionDTO {

  private int codigo;
  private MesaSillaDTO silla;

  public int getCodigo() {
    return codigo;
  }

  public void setCodigo(int codigo) {
    this.codigo = codigo;
  }

  public MesaSillaDTO getSilla() {
    return silla;
  }

  public void setSilla(MesaSillaDTO silla) {
    this.silla = silla;
  }

  @Override
  public String toString() {
    return "ReservacionDTO{" + "codigo=" + codigo + ", silla=" + silla + '}';
  }

}
