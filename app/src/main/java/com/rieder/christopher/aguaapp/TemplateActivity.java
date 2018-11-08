package com.rieder.christopher.aguaapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.Gson;
import com.rieder.christopher.aguaapp.DomainClasses.TemplateRecorrido;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

public class TemplateActivity extends AppCompatActivity {

    private TemplatePagerAdapter mAdapter;
    private ViewPager mViewPager;
    private TemplateRecorrido[] templates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template);

        RetrieveTemplates task = new RetrieveTemplates();
        task.execute();
    }

    private void onJsonDataRetrieved() {
        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.template_view_pager);
        mAdapter = new TemplatePagerAdapter(this, getSupportFragmentManager(), templates);
        mViewPager.setAdapter(mAdapter);

        TabLayout tabLayout = findViewById(R.id.template_tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }


    private class RetrieveTemplates extends AsyncTask<String, Void, TemplateRecorrido[]> {

        @Override
        protected TemplateRecorrido[] doInBackground(String... urls) {
            TemplateRecorrido[] templatesFromServer;

            String BASE_URL = "http://192.168.0.16:3000/api/"; // TODO: VER EN DONDE PONER...
            Gson gson = new Gson();
            try {

                URL checkLocalServer = new URL("http://192.168.0.16:3000/");
                String hello_world = makeHttpRequest(checkLocalServer);

                if (!hello_world.equals("Hello World!")) {
                    BASE_URL = "http://tophercasa.ddns.net:3000/api/"; // TODO: VER EN DONDE PONER...
                }

                // GET AND BUILD PRODUCTOS ---------------------------------------------------------
                // Hardcodeado a proposito. Si hay que agregar productos, hay que cambiar la UI
                URL url3 = new URL(BASE_URL + "recorridoTemplates");
                String jsonProductoResponse = makeHttpRequest(url3);
                templatesFromServer = gson.fromJson(jsonProductoResponse, TemplateRecorrido[].class);

                // TODO: PERMITIR AGREGAR O QUITAR CLIENTES. *LUEGO* DE CARGAR LOS DEL TEMPLATE.

                // TODO: ESTO SE HACE CUANDO ??? HAY QUE HABLAR CON EL REPARTIDOR
                // GET ENVASES EN COMODATO DE CADA CLIENTE.
                // COMO SE VAN A PODER AGREGAR O QUITAR CLIENTES, HAY QUE OBTENERLO DE LOS CLIENTES.
                templates = templatesFromServer;
                return templatesFromServer;
            } catch (Exception e) {
                Log.e("ERROR EN HTTP GET", e.toString());
            }
            return null;
        }

        /**
         * Make an HTTP request to the given URL and return a String as the response.
         */
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

        protected void onPostExecute(TemplateRecorrido[] feed) {
            templates = feed;
            onJsonDataRetrieved();
        }
    }
}
