package com.rieder.christopher.aguaapp;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.rieder.christopher.aguaapp.DomainClasses.Recorrido;
import com.rieder.christopher.aguaapp.DomainClasses.Venta;

import java.util.ArrayList;

class VentaPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<Venta> ventas;
    private Recorrido recorrido;

    VentaPagerAdapter(Context ctx, FragmentManager fm, Recorrido recorrido) {
        super(fm);
        this.recorrido = recorrido;
        this.ventas = recorrido.getVentas();
    }

    int getIndexClosestToLocation(double latitud, double longitud) {
        int idx = 0;
        double shortestDistance = Double.MAX_VALUE;

        for (int i = 0; i < ventas.size(); i++) {
            Venta v = ventas.get(i);
            double distance = v.getCliente().getLocationDistance(latitud, longitud);

            if (distance < shortestDistance) {
                shortestDistance = distance;
                idx = i;
            }
        }
        return idx;
    }

    @Override
    public Fragment getItem(int i) {
        if (i == 0) {
            RecorridoFragment rf = new RecorridoFragment();
            rf.setRecorrido(this.recorrido);
            return rf;
        }
        VentaFragment rf = new VentaFragment();
        rf.setVenta(ventas.get(i - 1), i - 1);
        return rf;
    }

    @Override
    public int getCount() {
        return ventas.size() + 1;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "RECORRIDO";
        }
        return ventas.get(position - 1).getCliente().getNombre();
    }
}
