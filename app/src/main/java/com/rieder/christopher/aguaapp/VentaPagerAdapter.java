package com.rieder.christopher.aguaapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.rieder.christopher.aguaapp.domain.Recorrido;
import com.rieder.christopher.aguaapp.domain.Venta;

import java.util.ArrayList;

class VentaPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<Venta> ventas;
    private Recorrido recorrido;
    static final int TAB_INDEX_VENTA = 1;
    private int currIndex;
    private String[] pageTitles = {"RECORRIDO", "VENTA"};

    void setCurrIndex(int currIndex) {
        this.currIndex = currIndex;
    }

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
            Log.v("RECREATION", "recorrido creado");
            // TODO: este fragmento es recreado cada vez que se cambia de venta
            // Pero solo quiero que se recree la venta, no los demás fragmentos
            // Como hacerlo? con registerDataSetObserver() me parece, pero como?
            return rf;
        }
        VentaFragment rf = new VentaFragment();
        rf.setVenta(ventas.get(currIndex));
        return rf;
    }

    @Override
    public int getCount() {
        return pageTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return pageTitles[position];
    }

    // TODO: esto fuerza la recreacion de todos los fragmentos cada vez que
    // se llama notifyDataSetChanged(). Pero deberia recrear solo el fragmento de venta.
    // O Tal vez en la lista dejar remarcado la venta actual??
    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
}
