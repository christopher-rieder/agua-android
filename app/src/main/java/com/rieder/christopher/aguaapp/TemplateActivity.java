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
import com.rieder.christopher.aguaapp.DomainClasses.TemplateRecorrido;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

public class TemplateActivity extends AppCompatActivity {

    private TemplatePagerAdapter mAdapter;
    private ViewPager mViewPager;
    private TemplateRecorrido[] templates; // As an array because is less code for Gson
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
                proxy.payloadClientes(templates[mViewPager.getCurrentItem()].getClientes());
                return true;
            }
        });
    }

    private static class RetrieveTemplates extends AsyncTask<String, Void, TemplateRecorrido[]> {

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
        protected TemplateRecorrido[] doInBackground(String... urls) {
            TemplateRecorrido[] templatesFromServer;

            try {
                // TODO: FOR TESTING PURPOSES. BUT SERVER DOWN MUST BE HANDLED CORRECTLY
                URL checkLocalServer = new URL("http://192.168.0.16:3000/");
                String hello_world = makeHttpRequest(checkLocalServer);
                if (!hello_world.equals("Hello World!")) {
                    BASE_URL = "http://tophercasa.ddns.net:3000/api/";
                }

                // OBTENER TEMPLATES DE RECORRIDOS, CON LOS CLIENTES ASOCIADOS, DE LA API HTTP
                Gson gson = new Gson();
                URL url3 = new URL(BASE_URL + "recorridoTemplates");
                String jsonProductoResponse = makeHttpRequest(url3);
                templatesFromServer = gson.fromJson(jsonProductoResponse, TemplateRecorrido[].class);
                // AGREGAR O QUITAR CLIENTES SE VA A HACER EN OTRA VISTA.
                // EN ESTA VISTA SE SELECCIONA UN TEMPLATE COMO BASE Y NADA MAS.
                // SI HAY QUE HACER MODIFICACIONES, SE HACEN EN OTRO LADO.

                return templatesFromServer;
            } catch (Exception e) {
                Log.e("ERROR EN HTTP GET", e.toString());
            }
            return null;
        }

        protected void onPostExecute(TemplateRecorrido[] feed) {
            TemplateActivity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) return;

            activity.templates = feed;
            activity.mViewPager = activity.findViewById(R.id.template_view_pager);
            activity.mAdapter = new TemplatePagerAdapter(activity, activity.getSupportFragmentManager(), activity.templates);
            activity.mViewPager.setAdapter(activity.mAdapter);

            activity.tabLayout = activity.findViewById(R.id.template_tabs);
            activity.tabLayout.setupWithViewPager(activity.mViewPager);
            activity.tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        }

        /**
         * Make an HTTP request to the given URL and return a String as the response.
         */
        @SuppressWarnings("TryFinallyCanBeTryWithResources")
        private String makeHttpRequest(URL url) throws IOException {
            String jsonResponse = "";
            HttpURLConnection urlConnection = null;
            InputStream inputStream = null;
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setReadTimeout(10000 /* milliseconds */);
                urlConnection.setConnectTimeout(15000 /* milliseconds */);
                urlConnection.connect();
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } catch (IOException e) {
                Log.d("ERROR/HTTP-REQUEST:", e.toString());
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (inputStream != null) {
                    // function must handle java.io.IOException here
                    inputStream.close();
                }
            }
            return jsonResponse;
        }

        /**
         * Convert the {@link InputStream} into a String which contains the
         * whole JSON response from the server.
         */
        private String readFromStream(InputStream inputStream) throws IOException {
            StringBuilder output = new StringBuilder();
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line = reader.readLine();
                while (line != null) {
                    output.append(line);
                    line = reader.readLine();
                }
            }
            return output.toString();
        }
    }
}
