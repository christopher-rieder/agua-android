package com.rieder.christopher.aguaapp.domain;

import java.util.ArrayList;

public final class TemplateRecorrido {
    private final int recorridoTemplateID;
    private final String nombre;
    private final ArrayList<Cliente> clientes;

    public TemplateRecorrido(int recorridoTemplateID, String nombre, ArrayList<Cliente> clientes) {
        this.recorridoTemplateID = recorridoTemplateID;
        this.nombre = nombre;
        this.clientes = clientes;
    }

    public int getRecorridoTemplateID() {
        return recorridoTemplateID;
    }

    public ArrayList<Cliente> getClientes() {
        return clientes;
    }

    public String getNombre() {
        return nombre;
    }
}
