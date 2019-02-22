package com.rieder.christopher.aguaapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rieder.christopher.aguaapp.domain.Cliente;

import java.util.List;

class ClienteListAdapter extends RecyclerView.Adapter<ClienteListAdapter.ViewHolder> {

    private List<Cliente> objects;
    private Context context;

    public ClienteListAdapter(Context context, List<Cliente> objects) {
        this.objects = objects;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(
                LayoutInflater.from(context).inflate(
                        R.layout.cliente_list_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final Cliente currentCliente = objects.get(i);
        assert currentCliente != null;

        String firstLetter = currentCliente.getNombre().substring(0, 1);

        viewHolder.nameTextView.setText(currentCliente.getNombre());
        viewHolder.letterCircleView.setText(firstLetter);
        viewHolder.numberTextView.setText(currentCliente.getDomicilio());
    }

    @Override
    public int getItemCount() {
        return this.objects.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView;
        private TextView letterCircleView;
        private TextView numberTextView;
        private View parentView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.parentView = itemView;
            this.nameTextView = itemView.findViewById(R.id.cliente_list_nombre);
            this.letterCircleView = itemView.findViewById(R.id.circle);
            this.numberTextView = itemView.findViewById(R.id.cliente_list_domicilio);
        }
    }
}
