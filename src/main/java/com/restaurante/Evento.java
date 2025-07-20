package com.restaurante;

/**
 *
 * @author Avalos Albino Aldair Oswaldo 2222005685
 */
public class Evento {

  private int idEvento;
  private String nombre;
  private String tipo;

  public Evento(int idEvento, String nombre) {
    this.idEvento = idEvento;
    this.nombre = nombre;
  }

  public Evento(int idEvento, String nombre, String tipo) {
    this.idEvento = idEvento;
    this.nombre = nombre;
    this.tipo = tipo;
  }

  // Getters (required for JSON serialization by Javalin/Jackson)
  public int getIdEvento() {
    return idEvento;
  }

  public String getNombre() {
    return nombre;
  }

  public String getTipo() {
    return tipo;
  }

}
