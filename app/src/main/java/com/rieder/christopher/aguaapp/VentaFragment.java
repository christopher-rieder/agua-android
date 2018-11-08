package com.rieder.christopher.aguaapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.rieder.christopher.aguaapp.DomainClasses.DetalleVenta;
import com.rieder.christopher.aguaapp.DomainClasses.Venta;

public class VentaFragment extends Fragment {

    private Venta venta;
    private int i;

    public int getI() {
        return i;
    }

    public VentaFragment() {
        //empty fragment
    }

    public void setVenta(Venta venta, int i) {
        this.venta = venta;
        this.i = i;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_venta, container, false);

        // get TextViews and buttons;
        TextView venta_nombre_cliente = rootView.findViewById(R.id.venta_nombre_cliente);
        TextView venta_domicilio_cliente = rootView.findViewById(R.id.venta_domicilio_cliente);

        TextView venta_agua_cantidad = rootView.findViewById(R.id.venta_agua_cantidad);
        TextView venta_agua_precio = rootView.findViewById(R.id.venta_agua_precio);
        TextView venta_agua_comodato = rootView.findViewById(R.id.venta_agua_comodato_cantidad);
        Button incrementarAgua = rootView.findViewById(R.id.button_incrementar_agua);
        Button decrementarAgua = rootView.findViewById(R.id.button_decrementar_agua);

        TextView venta_soda_cantidad = rootView.findViewById(R.id.venta_soda_cantidad);
        TextView venta_soda_precio = rootView.findViewById(R.id.venta_soda_precio);
        TextView venta_soda_comodato = rootView.findViewById(R.id.venta_soda_comodato_cantidad);
        Button incrementarSoda = rootView.findViewById(R.id.button_incrementar_soda);
        Button decrementarSoda = rootView.findViewById(R.id.button_decrementar_soda);

        // set datos del cliente
        venta_nombre_cliente.setText(this.venta.getCliente().getNombre());
        venta_domicilio_cliente.setText(this.venta.getCliente().getDomicilio());

        // set datos y handlers of soda
        DetalleVenta soda = this.venta.getSoda();
        OnClick sodaClickHandler = new OnClick(soda, venta_soda_cantidad);

        venta_soda_precio.setText("$" + soda.getPrecioUnitario());
        venta_soda_cantidad.setText("" + soda.getCantidad());
        venta_soda_comodato.setText("" + soda.getEnvasesPrevios());
        incrementarSoda.setOnClickListener(sodaClickHandler);
        decrementarSoda.setOnClickListener(sodaClickHandler);

        // set datos y handlers of agua
        DetalleVenta agua = this.venta.getAgua();
        OnClick aguaClickHandler = new OnClick(agua, venta_agua_cantidad);

        venta_agua_precio.setText("$" + agua.getPrecioUnitario());
        venta_agua_cantidad.setText("" + agua.getCantidad());
        venta_agua_comodato.setText("" + agua.getEnvasesPrevios());
        incrementarAgua.setOnClickListener(aguaClickHandler);
        decrementarAgua.setOnClickListener(aguaClickHandler);

        FloatingActionButton fab = rootView.findViewById(R.id.venta_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, venta.getClienteLocation());
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

        return rootView;
    }

    private final class OnClick implements View.OnClickListener {
        private final DetalleVenta dv;
        private final TextView cantidadTextView;

        OnClick(DetalleVenta dv, TextView cantidadTextView) {
            this.dv = dv;
            this.cantidadTextView = cantidadTextView;
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onClick(View v) {
            Button b = (Button) v;
            String str = (String) b.getText();

            if (str.equals("+")) {
                this.dv.incrementar();
            } else {
                this.dv.decrementar();
            }
            this.cantidadTextView.setText("" + dv.getCantidad());
        }
    }
}
