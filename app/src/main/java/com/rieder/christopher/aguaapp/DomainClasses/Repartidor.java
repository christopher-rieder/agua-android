package com.rieder.christopher.aguaapp.DomainClasses;

import org.json.JSONException;
import org.json.JSONObject;

public final class Repartidor {

    private final int repartidorID;
    private final String nombre;
    //TODO: LOGIN LOGIC

    public Repartidor(JSONObject source) throws JSONException {
        // {"results":[{"repartidorID":1,"nombre":"TOPHER","isDadoDeBaja":0}]}
        this.nombre = source.getString("nombre");
        this.repartidorID = source.getInt("repartidorID");
    }

    public String getNombre() {
        return nombre;
    }
}
