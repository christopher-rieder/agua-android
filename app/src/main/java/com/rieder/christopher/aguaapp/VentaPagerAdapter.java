package com.rieder.christopher.aguaapp;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.rieder.christopher.aguaapp.DomainClasses.Recorrido;
import com.rieder.christopher.aguaapp.DomainClasses.Venta;

import java.util.ArrayList;


public class VentaPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<Venta> ventas;

    VentaPagerAdapter(Context ctx, FragmentManager fm, Recorrido recorrido) {
        super(fm);
        this.ventas = recorrido.getVentas();
    }


    public int getIndexClosestToLocation(double latitud, double longitud) {
        int idx = 0;
        double shortestDistance = Double.MAX_VALUE;

        for (int i = 0; i < ventas.size(); i++) {
            Venta v = ventas.get(i);
            double distance = v.getCliente().getLocationDistance(latitud, longitud);

            if (distance < shortestDistance) {
                shortestDistance = distance;
                idx = i;
                Log.i("COORDINATES", "Distance: " + distance + " ; index: " + i + "\n" + v.getCliente().getNombre());
            }
        }
        return idx;
    }

    @Override
    public Fragment getItem(int i) {
        VentaFragment rf = new VentaFragment();
        rf.setVenta(ventas.get(i), i);
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
