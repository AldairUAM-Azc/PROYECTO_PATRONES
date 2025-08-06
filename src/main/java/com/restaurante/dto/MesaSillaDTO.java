package com.restaurante.dto;

/**
 *
 * @author Avalos Albino Aldair Oswaldo 2222005685
 */
public class MesaSillaDTO {

  private int mesa;
  private String silla;

  public int getMesa() {
    return mesa;
  }

  public void setMesa(int mesa) {
    this.mesa = mesa;
  }

  public String getSilla() {
    return silla;
  }

  public void setSilla(String silla) {
    this.silla = silla;
  }

  @Override
  public String toString() {
    return "MesaSilla{" + "mesa=" + mesa + ", silla=" + silla + '}';
  }

}
