package com.rieder.christopher.aguaapp.DomainClasses;

import java.util.ArrayList;

public final class Recorrido {

    private final int recorridoID;
    private final String nombre;
    private final String fecha;
    private final int repartidorID;
    private final int cantidadEnvasesLlenosInicial;
    private final int cantidadEnvasesVaciosInicial;
    private int cantidadEnvasesLlenosFinal = 0;
    private int cantidadEnvasesVaciosFinal = 0;
    private ArrayList<Venta> ventas;

    public Recorrido(int recorridoID, String nombre, String fecha, int repartidorID, int cantidadEnvasesLlenosInicial, int cantidadEnvasesVaciosInicial) {
        this.recorridoID = recorridoID;
        this.nombre = nombre;
        this.fecha = fecha;
        this.repartidorID = repartidorID;
        this.cantidadEnvasesLlenosInicial = cantidadEnvasesLlenosInicial;
        this.cantidadEnvasesVaciosInicial = cantidadEnvasesVaciosInicial;
    }

    public void buildVentas(ArrayList<Cliente> clientes){
        this.ventas = new ArrayList<>();
        for (Cliente cliente : clientes) {
            Venta v = new Venta(cliente);
            this.ventas.add(v);
        }
    }

    public ArrayList<Venta> getVentas() {
        return ventas;
    }

    public String getNombre() {
        return nombre;
    }
}