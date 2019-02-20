package com.rieder.christopher.aguaapp;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.rieder.christopher.aguaapp.domain.TemplateRecorrido;

import java.util.ArrayList;

class TemplatePagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<TemplateRecorrido> templates;

    TemplatePagerAdapter(Context ctx, FragmentManager fm, ArrayList<TemplateRecorrido> templates) {
        super(fm);
        this.templates = templates;
    }

    @Override
    public int getCount() {
        return templates.size();
    }

    @Override
    public Fragment getItem(int i) {
        TemplateFragment rf = new TemplateFragment();
        rf.setTemplate(templates.get(i));
        return rf;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return templates.get(position).getNombre();
    }
}
