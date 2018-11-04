package com.rieder.christopher.aguaapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rieder.christopher.aguaapp.DomainClasses.Recorrido;

public class RecorridoFragment extends Fragment {

    private Recorrido mRecorrido;

    public RecorridoFragment() {
        //empty fragment
    }

    public void setRecorrido(Recorrido recorrido) {
        mRecorrido = recorrido;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recorrido, container, false);

        TextView nameTextView = rootView.findViewById(R.id.section_label);
        nameTextView.setText(mRecorrido.getTest());
        return rootView;
    }
}
