package com.restaurante.dto;

import java.util.List;

/**
 *
 * @author Avalos Albino Aldair Oswaldo 2222005685
 */
public class DatosDTO {

  private int sembrado;
  private List<MesaSillaDTO> listaMesaSilla;
  private String tipo;

  public int getSembrado() {
    return sembrado;
  }

  public void setSembrado(int sembrado) {
    this.sembrado = sembrado;
  }

  public List<MesaSillaDTO> getListaMesaSilla() {
    return listaMesaSilla;
  }

  public void setListaMesaSilla(List<MesaSillaDTO> listaMesaSilla) {
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
