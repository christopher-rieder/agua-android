package com.rieder.christopher.aguaapp.domain;

public class EnvasesEnComodato {

    private final int clienteID;
    private final int productoID;
    private final int cantidad;

    public EnvasesEnComodato(int clienteID, int productoID, int cantidad) {
        this.clienteID = clienteID;
        this.productoID = productoID;
        this.cantidad = cantidad;
    }

    public int getClienteID() {
        return clienteID;
    }

    public int getProductoID() {
        return productoID;
    }

    public int getCantidad() {
        return cantidad;
    }
}
