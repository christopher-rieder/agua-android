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

        TextView venta_agua_cantidad = rootView.findViewById(R.id.venta_agua_cantidad);
        TextView venta_agua_precio = rootView.findViewById(R.id.venta_agua_precio);
        // TextView venta_agua_text = rootView.findViewById(R.id.venta_agua_text);
        TextView venta_soda_cantidad = rootView.findViewById(R.id.venta_soda_cantidad);
        TextView venta_soda_precio = rootView.findViewById(R.id.venta_soda_precio);
        // TextView venta_soda_text = rootView.findViewById(R.id.venta_soda_text);
        TextView venta_nombre_cliente = rootView.findViewById(R.id.venta_nombre_cliente);
        TextView venta_domicilio_cliente = rootView.findViewById(R.id.venta_domicilio_cliente);

        venta_nombre_cliente.setText(this.venta.getCliente().getNombre());
        venta_domicilio_cliente.setText(this.venta.getCliente().getDomicilio());

        venta_soda_precio.setText("" + this.venta.getDetallesVenta().get(0).getPrecioUnitario());
        venta_soda_cantidad.setText("" + this.venta.getDetallesVenta().get(0).getCantidad());

        venta_agua_precio.setText("" + this.venta.getDetallesVenta().get(1).getPrecioUnitario());
        venta_agua_cantidad.setText("" + this.venta.getDetallesVenta().get(1).getCantidad());

        return rootView;
    }
}
