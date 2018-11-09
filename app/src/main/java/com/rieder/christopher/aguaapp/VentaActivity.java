package com.rieder.christopher.aguaapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jinais.gnlib.android.launcher.GNLauncher;
import com.rieder.christopher.aguaapp.DomainClasses.Cliente;
import com.rieder.christopher.aguaapp.DomainClasses.Producto;
import com.rieder.christopher.aguaapp.DomainClasses.Recorrido;
import com.rieder.christopher.aguaapp.DomainClasses.Venta;

import org.ankit.gpslibrary.MyTracker;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VentaActivity extends AppCompatActivity implements IPayload {

    private Recorrido recorrido;
    private File file;
    private VentaPagerAdapter mAdapter;
    private ViewPager mViewPager;
    private List<Cliente> clientes = new ArrayList<>();

    private static final int REQUEST_CODE_PERMISSION = 2;
    private String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;

    // TODO: ONRESUME ejecutar getLocation() . Cuando se abre la app luego de que se suspendio
    // porque se apago el telefono, se cambio de app, etc. Aunque puede ser molesto cuando se vuelve
    // luego de un intent a googlemaps.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venta);

        /*RetrieveRecorrido task = new RetrieveRecorrido();
        task.execute();*/

        FloatingActionButton fab2 = findViewById(R.id.venta_fab2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VentaActivity.this.getLocation();
            }
        });

        GNLauncher.get().ping(this);
    }

    private void onJsonDataRetrieved() {
        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.venta_view_pager);
        mAdapter = new VentaPagerAdapter(this, getSupportFragmentManager(), recorrido);
        mViewPager.setAdapter(mAdapter);

        TabLayout tabLayout = findViewById(R.id.venta_tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    private void writeJsonToFile() {
        Gson gson = new Gson();
        file = new File("/mnt/sdcard/toto.json");
        String jsonResult = gson.toJson(recorrido);
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(file, false), 1024);
            out.write(jsonResult);
            out.newLine();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recorrido, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_write_to_json_file) {
            writeJsonToFile();
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Result Json escrito a: " + file.getAbsolutePath(),
                    Toast.LENGTH_SHORT);
            toast.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.i("PERMISSIONS", "Permission Granted");
                } else {
                    Log.i("PERMISSIONS", "Permission Denied !!!");
                    Log.d("PERMISSIONS", "Permission Denied !!!");
                }
                break;
        }
    }

    @Override
    public void payloadClientes(ArrayList<Cliente> clientes) {
        this.clientes = clientes;
        RetrieveRecorrido task = new RetrieveRecorrido();
        task.execute();
    }

    private Uri getLocation() {
        MyTracker tracker = new MyTracker(this);
        double latitud = tracker.getLatitude();
        double longitud = tracker.getLongitude();

        Uri coordinates = Uri.parse("geo:" + latitud + "," + longitud);
        Log.v("COORDINATES", latitud + "," + longitud);

        int idx = this.mAdapter.getIndexClosestToLocation(latitud, longitud);
        this.mViewPager.setCurrentItem(idx);
        return coordinates;
    }

    private class RetrieveRecorrido extends AsyncTask<String, Void, Recorrido> {

        // TODO: ESTO IRIA EN TEMPLATE_RECORRIDO.

        @Override
        protected Recorrido doInBackground(String... urls) {
            Recorrido newRecorrido = new Recorrido("GET FROM TEMPLATE", "TODAY", 1, 0, 60);

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
                URL url3 = new URL(BASE_URL + "productos");
                String jsonProductoResponse = makeHttpRequest(url3);
                Producto[] productos = gson.fromJson(jsonProductoResponse, Producto[].class);
                Producto agua = productos[0];
                Producto soda = productos[1];

                // GET AND BUILD CLIENTES & VENTAS -------------------------------------------------
                newRecorrido.buildVentas(clientes);


                // TODO: PERMITIR AGREGAR O QUITAR CLIENTES. *LUEGO* DE CARGAR LOS DEL TEMPLATE.
                // ME PARECE QUE VA A SER MEJOR HACERLO EN VentaActivity.
                // Y TENER UN FRAGMENT, ACTIVITY O LO QUE SEA PARA AGREGAR O QUITAR CLIENTES.

                // TODO: PERMITIR AGREGAR O QUITAR CLIENTES. *LUEGO* DE CARGAR LOS DEL TEMPLATE.

                // TODO: ESTO SE HACE CUANDO ??? HAY QUE HABLAR CON EL REPARTIDOR
                // GET ENVASES EN COMODATO DE CADA CLIENTE.
                // COMO SE VAN A PODER AGREGAR O QUITAR CLIENTES, HAY QUE OBTENERLO DE LOS CLIENTES.
                for (Venta venta : newRecorrido.getVentas()) {
                    Map<Producto, Integer> values = new HashMap<>();

                    Integer cantidadAgua = (int) Math.ceil(Math.random() * 3); //TODO: GET FROM DB
                    Integer cantidadSoda = (int) Math.ceil(Math.random() * 7); //TODO: GET FROM DB

                    values.put(soda, cantidadSoda);
                    values.put(agua, cantidadAgua);

                    venta.buildDetalleVentas(values);
                }
            } catch (Exception e) {
                Log.e("ERROR EN HTTP GET", e.toString());
            }
            return newRecorrido;
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

        protected void onPostExecute(Recorrido feed) {
            recorrido = feed;
            onJsonDataRetrieved();
            writeJsonToFile();
        }
    }
}
