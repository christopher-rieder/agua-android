package com.rieder.christopher.aguaapp;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.rieder.christopher.aguaapp.DomainClasses.Recorrido;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.ArrayList;


public class RecorridoPagerAdapter extends FragmentPagerAdapter {

    private JSONArray recorridos;

    RecorridoPagerAdapter(Context ctx, FragmentManager fm, JSONArray recorridos) {
        super(fm);
        this.recorridos = recorridos;
    }

    public int getIndexClosestToLocation(double latitud, double longitud) {
        int idx = 0;
        double shortestDistance = Double.MAX_VALUE;
        Recorrido r;

        for (int i = 0; i < recorridos.length(); i++) {
            try {
                JSONObject client = recorridos.getJSONObject(i);
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
    }


    @Override
    public Fragment getItem(int i) {
        RecorridoFragment rf = new RecorridoFragment();
        int idx = 0;
        Recorrido r = null;
        try {
            JSONObject client = recorridos.getJSONObject(i);
            r = new Recorrido(client.toString(4));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        rf.setRecorrido(r);
        return rf;
    }

    @Override
    public int getCount() {
        return recorridos.length();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String str = "ERROR";
        try {
            str = recorridos.getJSONObject(position).getString("nombre");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return str;
    }
}
