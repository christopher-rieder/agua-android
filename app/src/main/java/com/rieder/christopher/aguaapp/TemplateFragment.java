package com.rieder.christopher.aguaapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.rieder.christopher.aguaapp.DomainClasses.Cliente;
import com.rieder.christopher.aguaapp.DomainClasses.TemplateRecorrido;

import java.util.ArrayList;

public class TemplateFragment extends Fragment {

    private TemplateRecorrido template;

    public void setTemplate(TemplateRecorrido template) {
        this.template = template;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.cliente_list_view, container, false);

        final ArrayList<Cliente> clientes = template.getClientes();

        ClienteListAdapter itemsAdapter = new ClienteListAdapter(this.getContext(), clientes);
        ListView listView = rootView.findViewById(R.id.cliente_list_view);
        listView.setAdapter(itemsAdapter);


        return rootView;
    }


}
