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
    private int mesaNumero;
    private String sillaLetra;
    private boolean sillaEstado;
    private double precio;

    public EstadoSillas() {
    }

    public String getEventoNombre() {
        return eventoNombre;
    }

    public void setEventoNombre(String eventoNombre) {
        this.eventoNombre = eventoNombre;
    }

    public int getMesaNumero() {
        return mesaNumero;
    }

    public void setMesaNumero(int mesaNumero) {
        this.mesaNumero = mesaNumero;
    }

    public String getSillaLetra() {
        return sillaLetra;
    }

    public void setSillaLetra(String sillaLetra) {
        this.sillaLetra = sillaLetra;
    }

    public boolean isSillaEstado() {
        return sillaEstado;
    }

    public void setSillaEstado(boolean sillaEstado) {
        this.sillaEstado = sillaEstado;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

}
