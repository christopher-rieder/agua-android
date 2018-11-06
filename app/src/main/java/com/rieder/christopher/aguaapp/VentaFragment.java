package com.rieder.christopher.aguaapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rieder.christopher.aguaapp.DomainClasses.Venta;

public class VentaFragment extends Fragment {

    private Venta venta;

    public VentaFragment() {
        //empty fragment
    }

    public void setRecorrido(Venta venta) {
        this.venta = venta;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_venta, container, false);

        TextView nameTextView = rootView.findViewById(R.id.recorrido_textView);
        nameTextView.setText(this.venta.toString());
        return rootView;
    }
}
