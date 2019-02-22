package com.rieder.christopher.aguaapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.rieder.christopher.aguaapp.domain.DetalleVenta;

import java.util.List;

public class VentaListAdapter extends RecyclerView.Adapter<VentaListAdapter.ViewHolder> {

    private List<DetalleVenta> objects;
    private Context context;

    VentaListAdapter(Context context, List<DetalleVenta> objects) {
        this.context = context;
        this.objects = objects;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(
                LayoutInflater.from(context).inflate(
                        R.layout.venta_list_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final DetalleVenta dv = objects.get(i);
        assert dv != null;

        // SET TEXTVIEWS WITH DETALLE_VENTA DATA
        viewHolder.venta_producto_nombre.setText(dv.getProducto());
        viewHolder.venta_producto_cantidad.setText(String.valueOf(dv.getCantidad()));
        viewHolder.venta_producto_precio.setText("$" + dv.getPrecioUnitario() * dv.getCantidad());
        viewHolder.venta_producto_comodato.setText(String.valueOf(dv.getEnvasesPrevios()));

        OnClick clickHandler = new OnClick(dv, viewHolder.venta_producto_cantidad, viewHolder.venta_producto_precio);

        viewHolder.incrementar.setOnClickListener(clickHandler);
        viewHolder.decrementar.setOnClickListener(clickHandler);
    }

    @Override
    public int getItemCount() {
        return this.objects.size();
    }

    private final class OnClick implements View.OnClickListener {
        private final DetalleVenta dv;
        private final TextView cantidadTextView;
        private final TextView precioTotal;

        OnClick(DetalleVenta dv, TextView cantidadTextView, TextView precioTotal) {
            this.dv = dv;
            this.cantidadTextView = cantidadTextView;
            this.precioTotal = precioTotal;
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
            this.precioTotal.setText("$" + dv.getPrecioUnitario() * dv.getCantidad());
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView venta_producto_nombre;
        private TextView venta_producto_cantidad;
        private TextView venta_producto_precio;
        private TextView venta_producto_comodato;
        private Button incrementar;
        private Button decrementar;

        public ViewHolder(@NonNull View view) {
            super(view);
            this.venta_producto_nombre = view.findViewById(R.id.venta_producto_nombre);
            this.venta_producto_cantidad = view.findViewById(R.id.venta_cantidad);
            this.venta_producto_precio = view.findViewById(R.id.venta_precio);
            this.venta_producto_comodato = view.findViewById(R.id.venta_comodato_cantidad);
            this.incrementar = view.findViewById(R.id.venta_button_incrementar);
            this.decrementar = view.findViewById(R.id.venta_button_decrementar);
        }
    }
}
