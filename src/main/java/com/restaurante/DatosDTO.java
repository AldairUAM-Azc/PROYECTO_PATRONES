package com.restaurante;

import java.util.List;

/**
 *
 * @author Avalos Albino Aldair Oswaldo 2222005685
 */
public class DatosDTO {

  private int sembrado;
  private List<MesaSilla> listaMesaSilla;
  private String tipo;

  public int getSembrado() {
    return sembrado;
  }

  public void setSembrado(int sembrado) {
    this.sembrado = sembrado;
  }

  public List<MesaSilla> getListaMesaSilla() {
    return listaMesaSilla;
  }

  public void setListaMesaSilla(List<MesaSilla> listaMesaSilla) {
    this.listaMesaSilla = listaMesaSilla;
  }

  public String getTipo() {
    return tipo;
  }

  public void setTipo(String tipo) {
    this.tipo = tipo;
  }

  @Override
  public String toString() {
    return "DatosDTO{" + "sembrado=" + sembrado + ", listaMesaSilla=" + listaMesaSilla + ", tipo=" + tipo + '}';
  }

}

class MesaSilla {

  private String mesa;
  private String silla;

  public String getMesa() {
    return mesa;
  }

  public void setMesa(String mesa) {
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
