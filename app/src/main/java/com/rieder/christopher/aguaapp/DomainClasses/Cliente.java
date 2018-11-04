package com.rieder.christopher.aguaapp.DomainClasses;

import org.json.JSONException;
import org.json.JSONObject;

public class Cliente {

    private final int clienteID;
    private final String nombre;
    private final String domicilio;
    private final String telefono;
    private final double longitud;
    private final double latitud;

    public Cliente(JSONObject source) throws JSONException {
        // {"clienteID":1,"nombre":"FEDE","domicilio":"Las Heras 933","telefono":null,"isDadoDeBaja":0,"latitud":null,"longitud":null}
        this.clienteID = source.getInt("clienteID");
        this.nombre = source.getString("nombre");
        this.domicilio = source.getString("domicilio");
        this.telefono = source.optString("telefono", "NOPE");
        this.longitud = source.optDouble("longitud", 0d);
        this.latitud = source.optDouble("latitud", 0d);
    }

    public String getNombre() {
        return nombre;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public String getTelefono() {
        return telefono;
    }

    public double getLongitud() {
        return longitud;
    }

    public double getLatitud() {
        return latitud;
    }
}
