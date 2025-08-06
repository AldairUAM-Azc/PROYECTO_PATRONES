/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.restaurante;

/**
 *
 * @author aldair
 */
public class ReservacionJSON {

  private int mesa;
  private String silla;

  public ReservacionJSON() {
  }

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
    return "ReservacionJSON{" + "mesa=" + mesa + ", silla=" + silla + '}';
  }

}
