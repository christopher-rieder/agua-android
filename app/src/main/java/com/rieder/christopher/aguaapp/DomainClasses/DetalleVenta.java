package com.rieder.christopher.aguaapp.DomainClasses;

public class DetalleVenta {

    private Producto producto;
    private double precioUnitario;
    private int envasesDevueltos;
    private int envasesPrestados;


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
}
