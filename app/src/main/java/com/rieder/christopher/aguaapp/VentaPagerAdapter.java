package com.rieder.christopher.aguaapp;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.rieder.christopher.aguaapp.DomainClasses.Recorrido;
import com.rieder.christopher.aguaapp.DomainClasses.Venta;

import org.json.JSONException;

import java.util.ArrayList;


public class VentaPagerAdapter extends FragmentPagerAdapter {

    private Recorrido recorrido;
    private ArrayList<Venta> ventas;

    VentaPagerAdapter(Context ctx, FragmentManager fm, Recorrido recorrido) {
        super(fm);
        this.recorrido = recorrido;
        this.ventas = recorrido.getVentas();
    }

    /*
    public int getIndexClosestToLocation(double latitud, double longitud) {
        int idx = 0;
        double shortestDistance = Double.MAX_VALUE;
        Recorrido r;

        for (int i = 0; i < recorridos.size(); i++) {
            try {
                Recorrido client = recorridos.getJSONObject(i);
                r = new Recorrido(client.toString(4));
                double distance = r.getLocationDistance(latitud, longitud);

                if (distance < shortestDistance) {
                    shortestDistance = distance;
                    idx = i;
                    Log.i("COORDINATES", "Distance: " + distance + " ; index: " + i+"\n"+r.getTest());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return idx;
    }*/


    @Override
    public Fragment getItem(int i) {
        VentaFragment rf = new VentaFragment();
        rf.setRecorrido(ventas.get(i));
        return rf;
    }

    @Override
    public int getCount() {
        return ventas.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return ventas.get(position).getCliente().getNombre();
    }
}
