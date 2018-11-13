package com.rieder.christopher.aguaapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.rieder.christopher.aguaapp.DomainClasses.Cliente;
import com.rieder.christopher.aguaapp.DomainClasses.Recorrido;
import com.rieder.christopher.aguaapp.DomainClasses.Venta;

import java.util.ArrayList;

public class RecorridoFragment extends Fragment {

    private Recorrido recorrido;

    public void setRecorrido(Recorrido recorrido) {
        this.recorrido = recorrido;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.cliente_list_view, container, false);

        final ArrayList<Cliente> clientes = new ArrayList<>();
        for (Venta venta : recorrido.getVentas()) {
            clientes.add(venta.getCliente());
        }

        ClienteListAdapter itemsAdapter = new ClienteListAdapter(this.getContext(), clientes);
        ListView listView = rootView.findViewById(R.id.cliente_list_view);
        listView.setAdapter(itemsAdapter);
        return rootView;
    }
}
