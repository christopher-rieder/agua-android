package com.rieder.christopher.aguaapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.rieder.christopher.aguaapp.domain.Cliente;
import com.rieder.christopher.aguaapp.domain.Venta;

import java.util.List;

class VentaListAdapter extends RecyclerView.Adapter<VentaListAdapter.ViewHolder> {

    private List<Venta> objects;
    private Context context;

    public VentaListAdapter(Context context, List<Venta> objects) {
        this.objects = objects;
        this.context = context;
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
        final Venta venta = objects.get(i);
        assert venta != null;
        final Cliente cliente = venta.getCliente();

        String firstLetter = cliente.getNombre().substring(0, 1);
        StringBuilder detalles = new StringBuilder();
        detalles.append("\uD83C\uDF7A <- ")
                .append(venta.getDetallesVenta().get(0).getEnvasesPrevios())
                .append(" / ")
                .append("-> ")
                .append(venta.getDetallesVenta().get(0).getCantidad());
        detalles.append(" || ");
        detalles.append("\uD83D\uDCA7 <- ")
                .append(venta.getDetallesVenta().get(1).getEnvasesPrevios())
                .append(" / ")
                .append("-> ")
                .append(venta.getDetallesVenta().get(1).getCantidad());

        viewHolder.nameTextView.setText(cliente.getNombre());
        viewHolder.letterCircleView.setText(firstLetter);
        viewHolder.numberTextView.setText(cliente.getDomicilio());
        viewHolder.ventaDetailsTextView.setText(detalles);
    }

    @Override
    public int getItemCount() {
        return this.objects.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView;
        private TextView letterCircleView;
        private TextView numberTextView;
        private TextView ventaDetailsTextView;
        private Button ventaListButton;
        private View parentView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.parentView = itemView;
            this.nameTextView = itemView.findViewById(R.id.cliente_list_nombre);
            this.letterCircleView = itemView.findViewById(R.id.circle);
            this.numberTextView = itemView.findViewById(R.id.cliente_list_domicilio);
            this.ventaDetailsTextView = itemView.findViewById(R.id.venta_list_detalles);
            this.ventaListButton = itemView.findViewById(R.id.venta_list_button);
        }
    }
}
