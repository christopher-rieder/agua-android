package com.rieder.christopher.aguaapp;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import org.json.JSONArray;
import org.json.JSONException;


public class TemplatePagerAdapter extends FragmentPagerAdapter {
    private JSONArray templates;

    TemplatePagerAdapter(Context ctx, FragmentManager fm, JSONArray templates) {
        super(fm);
        this.templates = templates;
    }

    @Override
    public int getCount() {
        return templates.length();
    }

    @Override
    public Fragment getItem(int i) {
        TemplateFragment rf = new TemplateFragment();/*
        int idx = 0;
        Recorrido r = null;
        try {
            JSONObject client = recorridos.getJSONObject(i);
            r = new Recorrido(client.toString(4));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        rf.setRecorrido(r);*/
        return rf;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String str = "ERROR";
        try {
            str = templates.getJSONObject(position).getString("nombre"); //TODO: CHECK JSON BLABLABLA
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return str;
    }
}
