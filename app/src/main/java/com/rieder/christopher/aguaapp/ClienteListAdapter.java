package com.rieder.christopher.aguaapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.rieder.christopher.aguaapp.domain.Cliente;

import java.util.List;

class ClienteListAdapter extends ArrayAdapter<Cliente> {

    ClienteListAdapter(Context context, List<Cliente> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.cliente_list_item, parent, false);
        }
        final Cliente currentCliente = getItem(position);
        assert currentCliente != null;

        String firstLetter = currentCliente.getNombre().substring(0, 1);

        TextView nameTextView = listItemView.findViewById(R.id.cliente_list_nombre);
        nameTextView.setText(currentCliente.getNombre());

        TextView letterCircleView = listItemView.findViewById(R.id.circle);
        letterCircleView.setText(firstLetter);

        TextView numberTextView = listItemView.findViewById(R.id.cliente_list_domicilio);
        numberTextView.setText(currentCliente.getDomicilio());

        return listItemView;
    }
}
