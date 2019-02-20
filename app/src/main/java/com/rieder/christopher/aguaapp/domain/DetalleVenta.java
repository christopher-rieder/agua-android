package com.rieder.christopher.aguaapp.domain;

public final class DetalleVenta {

    private final String producto;
    private int cantidad;
    private final double precioUnitario;

    /* Envases que tiene el cliente en comodato actualmente.
       Es decir, envases que tiene que devolver */
    private final int envasesPrevios;

    public DetalleVenta(Producto key, Integer envasesPrevios) {
        this.precioUnitario = key.getPrecioUnitario();
        this.producto = key.getNombre();
        this.envasesPrevios = envasesPrevios;

        // precargar cantidad, suponiendo que el cliente va a pedir la misma cantidad que antes
        this.cantidad = envasesPrevios;
    }

    public String getProducto() {
        return producto;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public int getCantidad() {
        return cantidad;
    }

    public int getEnvasesPrevios() {
        return envasesPrevios;
    }

    public void incrementar() {
        this.cantidad += 1;
    }

    public void decrementar() {
        this.cantidad = Math.max(0, this.cantidad - 1);
    }
}
