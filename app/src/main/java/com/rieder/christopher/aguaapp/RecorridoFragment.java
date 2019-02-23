package com.rieder.christopher.aguaapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rieder.christopher.aguaapp.domain.Recorrido;
import com.rieder.christopher.aguaapp.domain.Venta;

import java.util.ArrayList;

public class RecorridoFragment extends Fragment {

    OnVentaClickListener callback;
    private Recorrido recorrido;

    // Override onAttach to make sure that the container activity has implemented the callback
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            callback = (OnVentaClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnVentaClickListener");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.cliente_list_view, container, false);

        final ArrayList<Venta> ventas = recorrido.getVentas();

        RecyclerView recyclerView = rootView.findViewById(R.id.cliente_recycler_view);
        recyclerView.setAdapter(new VentaListAdapter(this.getContext(), ventas));
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
//        recyclerView.setOnClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                callback.onVentaSelected(position);
//                return true;
//            }
//        });
        return rootView;
    }

    public void setRecorrido(Recorrido recorrido) {
        this.recorrido = recorrido;
    }

    public interface OnVentaClickListener {
        void onVentaSelected(int position);
    }
}
