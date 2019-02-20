package com.rieder.christopher.aguaapp.domain;

public final class Repartidor {

    private final int repartidorID;
    private final String nombre;
    //TODO: LOGIN LOGIC

    public Repartidor(int repartidorID, String nombre) {
        this.repartidorID = repartidorID;
        this.nombre = nombre;
    }

    public int getRepartidorID() {
        return repartidorID;
    }

    public String getNombre() {
        return nombre;
    }
}
