package com.rieder.christopher.aguaapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rieder.christopher.aguaapp.DomainClasses.Recorrido;

public class RecorridoFragment extends Fragment {

    private Recorrido recorrido;

    public RecorridoFragment() {
        //empty fragment
    }

    public void setRecorrido(Recorrido recorrido) {
        this.recorrido = recorrido;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recorrido, container, false);

        TextView nameTextView = rootView.findViewById(R.id.recorrido_textView);
        nameTextView.setText(this.recorrido.getTest());
        return rootView;
    }
}
