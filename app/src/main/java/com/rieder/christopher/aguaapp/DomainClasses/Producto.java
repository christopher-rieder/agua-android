package com.rieder.christopher.aguaapp.DomainClasses;

public final class Producto {

    private final int productoID;
    private final String nombre;
    private final int stock; //stock en deposito.
    private final double precioUnitario;

    public Producto(int productoID, String nombre, int stock, double precioUnitario) {
        this.productoID = productoID;
        this.nombre = nombre;
        this.stock = stock;
        this.precioUnitario = precioUnitario;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public int getProductoID() {
        return productoID;
    }

    public String getNombre() {
        return nombre;
    }

    public int getStock() {
        return stock;
    }

}
