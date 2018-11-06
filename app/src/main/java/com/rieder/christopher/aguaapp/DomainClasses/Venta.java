package com.rieder.christopher.aguaapp.DomainClasses;

import java.util.ArrayList;
import java.util.Map;

public final class Venta {

    private final Cliente cliente;
    private final String domicilio;
    private ArrayList<DetalleVenta> detallesVenta;

    public Venta(Cliente cliente) {
        this.cliente = cliente;
        String dom = cliente.getDomicilio();
        this.domicilio = dom;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void buildDetalleVentas(Map<Producto, Integer> values) {
        detallesVenta = new ArrayList<>();
        for (Map.Entry<Producto, Integer> value : values.entrySet()) {
            DetalleVenta dv = new DetalleVenta(value.getKey(), value.getValue());
            detallesVenta.add(dv);
        }
    }

    public ArrayList<DetalleVenta> getDetallesVenta() {
        return detallesVenta;
    }
}