package com.rieder.christopher.aguaapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rieder.christopher.aguaapp.domain.Cliente;
import com.rieder.christopher.aguaapp.domain.TemplateRecorrido;

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

        RecyclerView recyclerView = rootView.findViewById(R.id.cliente_recycler_view);
        recyclerView.setAdapter(new ClienteListAdapter(this.getContext(), clientes));
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        return rootView;
    }


}
