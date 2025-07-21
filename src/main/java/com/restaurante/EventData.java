package com.restaurante;

/**
 *
 * @author aldair
 */
class EventData {

    private int idEvento;
    private String tipo;
    private String nombre;

    public EventData() {
    }

    public EventData(int idEvento, String tipo, String nombre) {
        this.idEvento = idEvento;
        this.tipo = tipo;
        this.nombre = nombre;
    }

    public int getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(int idEvento) {
        this.idEvento = idEvento;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "EventData{" + "idEvento=" + idEvento + ", tipo=" + tipo + ", nombre=" + nombre + '}';
    }

}
