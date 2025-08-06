/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.restaurante.dto;

/**
 *
 * @author aldair
 */
public class PrecioDTO {

    private double VIP;
    private double Preferente;
    private double General;
    private double Laterales;

    public double getVIP() {
        return VIP;
    }

    public void setVIP(double VIP) {
        this.VIP = VIP;
    }

    public double getPreferente() {
        return Preferente;
    }

    public void setPreferente(double Preferente) {
        this.Preferente = Preferente;
    }

    public double getGeneral() {
        return General;
    }

    public void setGeneral(double General) {
        this.General = General;
    }

    public double getLaterales() {
        return Laterales;
    }

    public void setLaterales(double Laterales) {
        this.Laterales = Laterales;
    }

    @Override
    public String toString() {
        return "PrecioDTO{" + "VIP=" + VIP + ", Preferente=" + Preferente + ", General=" + General + ", Laterales=" + Laterales + '}';
    }

}
