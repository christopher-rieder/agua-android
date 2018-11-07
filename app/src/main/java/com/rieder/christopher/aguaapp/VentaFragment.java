package com.rieder.christopher.aguaapp;

import android.os.Bundle;
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
        TextView venta_agua_comodato = rootView.findViewById(R.id.venta_agua_comodato_cantidad);

        TextView venta_soda_cantidad = rootView.findViewById(R.id.venta_soda_cantidad);
        TextView venta_soda_precio = rootView.findViewById(R.id.venta_soda_precio);
        TextView venta_soda_comodato = rootView.findViewById(R.id.venta_soda_comodato_cantidad);


        TextView venta_nombre_cliente = rootView.findViewById(R.id.venta_nombre_cliente);
        TextView venta_domicilio_cliente = rootView.findViewById(R.id.venta_domicilio_cliente);

        venta_nombre_cliente.setText(this.venta.getCliente().getNombre());
        venta_domicilio_cliente.setText(this.venta.getCliente().getDomicilio());

        DetalleVenta soda = this.venta.getDetallesVenta().get(0);
        DetalleVenta agua = this.venta.getDetallesVenta().get(1);

        venta_soda_precio.setText("" + soda.getPrecioUnitario());
        venta_soda_cantidad.setText("" + soda.getCantidad());
        venta_soda_comodato.setText("" + soda.getEnvasesPrestados()); //TODO: OBTENER DE LA VENTA ANTERIOR

        venta_agua_precio.setText("" + agua.getPrecioUnitario());
        venta_agua_cantidad.setText("" + agua.getCantidad());
        venta_agua_comodato.setText("" + agua.getEnvasesPrestados()); //TODO: OBTENER DE LA VENTA ANTERIOR

        Button incrementarAgua = rootView.findViewById(R.id.button_incrementar_agua);
        Button decrementarAgua = rootView.findViewById(R.id.button_decrementar_agua);
        Button incrementarSoda = rootView.findViewById(R.id.button_incrementar_soda);
        Button decrementarSoda = rootView.findViewById(R.id.button_dencrementar_soda);

        OnClick aguaClickHandler = new OnClick(agua, venta_agua_cantidad);
        OnClick sodaClickHandler = new OnClick(soda, venta_soda_cantidad);

        incrementarAgua.setOnClickListener(aguaClickHandler);
        decrementarAgua.setOnClickListener(aguaClickHandler);

        incrementarSoda.setOnClickListener(sodaClickHandler);
        decrementarSoda.setOnClickListener(sodaClickHandler);


        return rootView;
    }

    private class OnClick implements View.OnClickListener {
        private DetalleVenta dv;
        private TextView cantidadTextView;

        public OnClick(DetalleVenta dv, TextView cantidadTextView) {
            this.dv = dv;
            this.cantidadTextView = cantidadTextView;
        }

        @Override
        public void onClick(View v) {
            Button b = (Button) v;
            String str = (String) b.getText();

            if (str.equals("+")) {
                dv.incrementar();
            } else {
                dv.decrementar();
            }
            this.cantidadTextView.setText("" + dv.getCantidad());

        }


    }
}
