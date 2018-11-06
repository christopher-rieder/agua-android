package com.rieder.christopher.aguaapp.DomainClasses;

public final class DetalleVenta {

    private String producto;
    private int cantidad;
    private double precioUnitario;
    private int envasesDevueltos;
    private int envasesPrestados;

    public DetalleVenta(Producto key, Integer value) {
        this.cantidad = value;
        this.precioUnitario = key.getPrecioUnitario();
        this.producto = key.getNombre();
    }


    public String getProducto() {
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
