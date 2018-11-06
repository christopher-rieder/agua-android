package com.rieder.christopher.aguaapp.DomainClasses;

public final class Producto {

    private final String nombre;
    private final int stock; //stock en deposito.

    public Producto(String nombre, int stock) {
        this.nombre = nombre;
        this.stock = stock;
    }

    public String getNombre() {
        return nombre;
    }

    public int getStock() {
        return stock;
    }
}
