package com.rieder.christopher.aguaapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.rieder.christopher.aguaapp.DomainClasses.DetalleVenta;

import java.util.List;

public class VentaListAdapter extends ArrayAdapter<DetalleVenta> {

    VentaListAdapter(Context context, List<DetalleVenta> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.venta_list_item, parent, false);
        }

        final DetalleVenta dv = getItem(position);
        assert dv != null;

        // SET TEXTVIEWS WITH DETALLE_VENTA DATA
        TextView venta_producto_nombre = listItemView.findViewById(R.id.venta_producto_nombre);
        venta_producto_nombre.setText("" + dv.getProducto());

        TextView venta_producto_cantidad = listItemView.findViewById(R.id.venta_cantidad);
        venta_producto_cantidad.setText("" + dv.getCantidad());

        TextView venta_producto_precio = listItemView.findViewById(R.id.venta_precio);
        venta_producto_precio.setText("$" + dv.getPrecioUnitario());

        TextView venta_producto_comodato = listItemView.findViewById(R.id.venta_comodato_cantidad);
        venta_producto_comodato.setText("" + dv.getEnvasesPrevios());

        // SET CLICK HANDLERS
        OnClick clickHandler = new OnClick(dv, venta_producto_cantidad);

        Button incrementar = listItemView.findViewById(R.id.venta_button_incrementar);
        incrementar.setOnClickListener(clickHandler);

        Button decrementar = listItemView.findViewById(R.id.venta_button_decrementar);
        decrementar.setOnClickListener(clickHandler);

        return listItemView;
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
