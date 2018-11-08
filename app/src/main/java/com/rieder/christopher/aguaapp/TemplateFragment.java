package com.rieder.christopher.aguaapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jinais.gnlib.android.launcher.GNLauncher;
import com.rieder.christopher.aguaapp.DomainClasses.TemplateRecorrido;

public class TemplateFragment extends Fragment {

    private TemplateRecorrido template;

    public void setTemplate(TemplateRecorrido template) {
        this.template = template;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_template, container, false);
        TextView nameTextView = rootView.findViewById(R.id.template_textView);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        nameTextView.setText(gson.toJson(template));
        nameTextView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // TODO: CREATE NEW VENTA_ACTIVITY CON EL TEMPLATE.

                GNLauncher launcher = GNLauncher.get();
                IPayload proxy = (IPayload) launcher.getProxy(getContext(), IPayload.class, VentaActivity.class);
                proxy.payloadClientes(template.getClientes());

                /*Intent intent = new Intent(getActivity(), VentaActivity.class);
                startActivity(intent);*/
                return true;
            }
        });
        /*

        ClienteListAdapter itemsAdapter = new ClienteListAdapter(this.getContext(), template.getClientes());


        ListView listView = rootView.findViewById(R.id.template_cliente_list);
        listView.setAdapter(itemsAdapter);
*/


        return rootView;
    }


}
