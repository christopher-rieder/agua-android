package com.rieder.christopher.aguaapp;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.rieder.christopher.aguaapp.DomainClasses.TemplateRecorrido;

public class TemplatePagerAdapter extends FragmentStatePagerAdapter {

    private TemplateRecorrido[] templates;

    TemplatePagerAdapter(Context ctx, FragmentManager fm, TemplateRecorrido[] templates) {
        super(fm);
        this.templates = templates;
    }

    @Override
    public int getCount() {
        return templates.length;
    }

    @Override
    public Fragment getItem(int i) {
        TemplateFragment rf = new TemplateFragment();
        rf.setTemplate(templates[i]);
        return rf;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return templates[position].getNombre();
    }
}
