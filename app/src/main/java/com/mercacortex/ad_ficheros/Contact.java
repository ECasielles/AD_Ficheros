package com.mercacortex.ad_ficheros;

import java.io.Serializable;

public class Contact implements Serializable {
    String nombre, telefono, email;

    public Contact(String nombre, String telefono, String email) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() {
        return nombre + ';' +
                telefono + ';' +
                email + ';';
    }
}
