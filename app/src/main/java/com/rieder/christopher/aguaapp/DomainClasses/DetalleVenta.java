package com.rieder.christopher.aguaapp.DomainClasses;

public final class DetalleVenta {

    private Producto producto;
    private int cantidad;
    private double precioUnitario;
    private int envasesDevueltos;
    private int envasesPrestados;

    public DetalleVenta(Producto key, Integer value) {
        this.producto = key;
        this.cantidad = value;
        this.precioUnitario = producto.getPrecioUnitario();
    }


    public Producto getProducto() {
        return producto;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public int getEnvasesDevueltos() {
        return envasesDevueltos;
    }

    public int getEnvasesPrestados() {
        return envasesPrestados;
    }

    public int getCantidad() {
        return cantidad;
    }
}
