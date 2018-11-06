package com.rieder.christopher.aguaapp.DomainClasses;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public final class Recorrido {

    private final int recorridoID;
    private final String nombre;
    private final String fecha;
    private final int repartidorID;
    private final int cantidadEnvasesLlenosInicial;
    private final int cantidadEnvasesVaciosInicial;
    private int cantidadEnvasesLlenosFinal;
    private int cantidadEnvasesVaciosFinal;
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

    }

    public ArrayList<Venta> getVentas() {
        return ventas;
    }

    public String getNombre() {
        return nombre;
    }

}
