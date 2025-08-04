/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.restaurante.adminagendareventos;

/**
 *
 * @author aldair
 */
public class Usuario {

    private String username;
    private String rol; // Podría ser "admin" o "cliente"

    public Usuario(String username, String rol) {
        this.username = username;
        this.rol = rol;
    }

    public boolean esAdmin() {
        return "admin".equalsIgnoreCase(this.rol);
    }
}
