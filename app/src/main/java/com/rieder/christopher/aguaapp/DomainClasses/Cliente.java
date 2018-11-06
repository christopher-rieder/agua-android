package com.rieder.christopher.aguaapp.DomainClasses;

public final class Cliente {

    private int clienteID;
    private String nombre;
    private String domicilio;
    private String telefono;
    private double longitud;
    private double latitud;

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

    // USA TEOREMA DE PITAGORAS A^2+B^2=C^2
    // ES UTIL PARA EL PROPOSITO DE ESTA APP, NO NOS INTERESA LA PRECISION
    public double getLocationDistance(double latitud, double longitud) {
        double difLatitud = Math.abs(this.latitud - latitud);
        double difLongitud = Math.abs(this.longitud - longitud);
        return Math.sqrt(difLatitud * difLatitud + difLongitud * difLongitud);
    }
}
