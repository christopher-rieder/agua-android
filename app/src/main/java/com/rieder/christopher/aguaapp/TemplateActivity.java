package com.rieder.christopher.aguaapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jinais.gnlib.android.launcher.GNLauncher;
import com.rieder.christopher.aguaapp.DomainClasses.Producto;
import com.rieder.christopher.aguaapp.DomainClasses.TemplateRecorrido;

import java.lang.ref.WeakReference;
import java.net.URL;

public class TemplateActivity extends AppCompatActivity {

    private TemplatePagerAdapter mAdapter;
    private ViewPager mViewPager;
    private TemplateRecorrido[] templates; // As an array because is less code for Gson
    private Producto[] productos;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template);
        new RetrieveTemplates(this).execute();

        final Button seleccionarRecorridoBtn = findViewById(R.id.seleccionar_recorrido_button);

        // Obtener lista de clientes gracias a la librería 'com.jinais.android:gnlib-android:1.1.0+@jar'
        seleccionarRecorridoBtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                GNLauncher launcher = GNLauncher.get();
                IPayload proxy = (IPayload) launcher.getProxy(seleccionarRecorridoBtn.getContext(), IPayload.class, VentaActivity.class);
                TemplateRecorrido currTemplate = templates[mViewPager.getCurrentItem()];

                proxy.payloadClientes(currTemplate, productos);
                return true;
            }
        });
    }

    private void updateData(String[] feed) {
        // process feed.
        String jsonRecorridoTemplate = feed[0];
        String jsonProductos = feed[1];

        Gson gson = new Gson();
        this.templates = gson.fromJson(jsonRecorridoTemplate, TemplateRecorrido[].class);
        this.productos = gson.fromJson(jsonProductos, Producto[].class);

        this.mViewPager = this.findViewById(R.id.template_view_pager);
        this.mAdapter = new TemplatePagerAdapter(this, this.getSupportFragmentManager(), this.templates);
        this.mViewPager.setAdapter(this.mAdapter);

        this.tabLayout = this.findViewById(R.id.template_tabs);
        this.tabLayout.setupWithViewPager(this.mViewPager);
        this.tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    private static class RetrieveTemplates extends AsyncTask<String, Void, String[]> {

        // TODO: REMOVE LATER, THIS IS FOR TESTING PURPOSES
        private String BASE_URL = "http://192.168.0.16:3000/api/";

        // SEE: https://stackoverflow.com/questions/44309241/warning-this-asynctask-class-should-be-static-or-leaks-might-occur
        private final WeakReference<TemplateActivity> activityReference;

        RetrieveTemplates(TemplateActivity context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected void onPreExecute() {
            TemplateActivity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) return;

            Toast.makeText(activity,
                    "Preparando para cargar datos...",
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        protected String[] doInBackground(String... urls) {
            try {
                // TODO: FOR TESTING PURPOSES. BUT SERVER DOWN MUST BE HANDLED CORRECTLY
                URL checkLocalServer = new URL("http://192.168.0.16:3000/");
                String hello_world = HttpHelper.makeHttpRequest(checkLocalServer);
                if (!hello_world.equals("Hello World!")) {
                    BASE_URL = "http://tophercasa.ddns.net:3000/api/";
                }

                // OBTENER TEMPLATES DE RECORRIDOS, CON LOS CLIENTES ASOCIADOS, DE LA API HTTP
                URL getRecorridoTemplates = new URL(BASE_URL + "recorridoTemplates");
                String jsonRecorridoTemplate = HttpHelper.makeHttpRequest(getRecorridoTemplates);
                // OBTENER LOS PRODUCTOS, PARA PASAR LUEGO COMO DATOS A LA VENTA ACTIVITY.
                URL getProductos = new URL(BASE_URL + "productos");
                String jsonProductos = HttpHelper.makeHttpRequest(getProductos);

                return new String[]{jsonRecorridoTemplate, jsonProductos};
            } catch (Exception e) {
                Log.e("ERROR EN HTTP GET", e.toString());
            }
            return null;
        }

        protected void onPostExecute(String[] feed) {
            TemplateActivity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) return;
            activity.updateData(feed);
        }
    }
}
