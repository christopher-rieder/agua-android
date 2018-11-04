package com.rieder.christopher.aguaapp.DomainClasses;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class Recorrido {

    private String jsonData; //TODO: FOR TESTING PURPOSES ONLY

    public Recorrido(String jsonData) {
        this.jsonData = jsonData;
    }

    public String getTest() {
        return jsonData;
    }

    public void setTest(String test) {
        this.jsonData = jsonData;
    }

    public String toString() {
        return jsonData;
    }

    public double getLocationDistance(double latitud, double longitud) {
        double distance = Double.MAX_VALUE;
        try {
            JSONObject data = new JSONObject(jsonData);
            double lat = data.getDouble("latitud");
            double lon = data.getDouble("longitud");

            // NOTE: THIS IS A VERY SIMPLIFIED CALCULATION. IT'S OK FOR THE PURPOSES OF THIS APP,
            // BUT BECAUSE WE DON'T NEED PRECISION, SO PYTHAGORAS IS OK.
            double difLatitud = Math.abs(lat - latitud);
            double difLongitud = Math.abs(lon - longitud);
            distance = Math.sqrt(difLatitud * difLatitud + difLongitud * difLongitud);
        } catch (JSONException e) {
            Log.e("JSON", e.toString());
        }
        return distance;
    }
}
