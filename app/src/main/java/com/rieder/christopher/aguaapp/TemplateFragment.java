package com.rieder.christopher.aguaapp;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rieder.christopher.aguaapp.DomainClasses.TemplateRecorrido;

public class TemplateFragment extends Fragment {

    private TemplateRecorrido template;

    public void setRecorrido(TemplateRecorrido template) {
        this.template = template;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_template, container, false);
        // FIXME: R.resources
        TextView nameTextView = rootView.findViewById(R.id.template_textView);
        nameTextView.setText("TODO: TODO");
        return rootView;
    }
}
