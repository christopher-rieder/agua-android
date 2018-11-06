package com.rieder.christopher.aguaapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;
import com.rieder.christopher.aguaapp.DomainClasses.Cliente;
import com.rieder.christopher.aguaapp.DomainClasses.Producto;
import com.rieder.christopher.aguaapp.DomainClasses.Recorrido;
import com.rieder.christopher.aguaapp.DomainClasses.Venta;

import org.ankit.gpslibrary.MyTracker;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VentaActivity extends AppCompatActivity {

    // Trailing slash is needed
    public static final String BASE_URL = "http://api.myservice.com/";
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private TabLayout tabLayout;
    VentaPagerAdapter mAdapter;
    private Recorrido recorrido;

    private static final int REQUEST_CODE_PERMISSION = 2;
    private String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;
    private File file;
    private double latitud;
    private double longitud;
    private Uri coordinates;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venta);

        RetrieveRecorrido task = new RetrieveRecorrido();
        task.execute();

        // -----------------------------------------------------------------------------------------
//        JSONArray results = null;
//        try {
//            results = new JSONArray("[{\"ventaID\":1,\"clienteID\":30,\"recorridoID\":1,\"domicilio\":\"88878 Roxbury Avenue\",\"nombre\":\"Cristobal Tallant\",\"latitud\":-31.341799,\"longitud\":-64.336649,\"telefono\":\"(3543)-025-720\",\"productoID\":null,\"producto\":null,\"envasesDevueltos\":null,\"envasesPrestados\":null,\"precioUnitario\":null},{\"ventaID\":2,\"clienteID\":235,\"recorridoID\":1,\"domicilio\":\"1 Morrow Court\",\"nombre\":\"Marigold Procter\",\"latitud\":-31.364839,\"longitud\":-64.343628,\"telefono\":\"(3543)-976-808\",\"productoID\":null,\"producto\":null,\"envasesDevueltos\":null,\"envasesPrestados\":null,\"precioUnitario\":null},{\"ventaID\":3,\"clienteID\":417,\"recorridoID\":1,\"domicilio\":\"668 Duke Point\",\"nombre\":\"Aeriela Rumsby\",\"latitud\":-31.33979,\"longitud\":-64.348326,\"telefono\":\"(351)-351-8164\",\"productoID\":null,\"producto\":null,\"envasesDevueltos\":null,\"envasesPrestados\":null,\"precioUnitario\":null},{\"ventaID\":4,\"clienteID\":421,\"recorridoID\":1,\"domicilio\":\"001 Carberry Parkway\",\"nombre\":\"Stephi Wasmuth\",\"latitud\":-31.352431,\"longitud\":-64.335147,\"telefono\":\"(3543)-828-708\",\"productoID\":null,\"producto\":null,\"envasesDevueltos\":null,\"envasesPrestados\":null,\"precioUnitario\":null},{\"ventaID\":5,\"clienteID\":486,\"recorridoID\":1,\"domicilio\":\"20435 Kim Parkway\",\"nombre\":\"Boy Fursey\",\"latitud\":-31.357382,\"longitud\":-64.32893,\"telefono\":\"(351)-067-6697\",\"productoID\":null,\"producto\":null,\"envasesDevueltos\":null,\"envasesPrestados\":null,\"precioUnitario\":null},{\"ventaID\":6,\"clienteID\":677,\"recorridoID\":1,\"domicilio\":\"710 Atwood Terrace\",\"nombre\":\"Neala McNern\",\"latitud\":-31.319316,\"longitud\":-64.331053,\"telefono\":\"(351)-949-9032\",\"productoID\":null,\"producto\":null,\"envasesDevueltos\":null,\"envasesPrestados\":null,\"precioUnitario\":null},{\"ventaID\":7,\"clienteID\":745,\"recorridoID\":1,\"domicilio\":\"090 Express Place\",\"nombre\":\"Brannon Valentine\",\"latitud\":-31.332532,\"longitud\":-64.342004,\"telefono\":\"(351)-281-9446\",\"productoID\":null,\"producto\":null,\"envasesDevueltos\":null,\"envasesPrestados\":null,\"precioUnitario\":null},{\"ventaID\":8,\"clienteID\":814,\"recorridoID\":1,\"domicilio\":\"58338 Heath Plaza\",\"nombre\":\"Dorisa Wilbore\",\"latitud\":-31.345061,\"longitud\":-64.331212,\"telefono\":\"(351)-974-2739\",\"productoID\":null,\"producto\":null,\"envasesDevueltos\":null,\"envasesPrestados\":null,\"precioUnitario\":null},{\"ventaID\":9,\"clienteID\":957,\"recorridoID\":1,\"domicilio\":\"4 Badeau Parkway\",\"nombre\":\"Fritz Fossick\",\"latitud\":-31.340159,\"longitud\":-64.335888,\"telefono\":\"(351)-655-6531\",\"productoID\":null,\"producto\":null,\"envasesDevueltos\":null,\"envasesPrestados\":null,\"precioUnitario\":null},{\"ventaID\":10,\"clienteID\":959,\"recorridoID\":1,\"domicilio\":\"61788 Grover Park\",\"nombre\":\"Lennie Grigorian\",\"latitud\":-31.336908,\"longitud\":-64.349311,\"telefono\":\"(351)-177-0911\",\"productoID\":null,\"producto\":null,\"envasesDevueltos\":null,\"envasesPrestados\":null,\"precioUnitario\":null},{\"ventaID\":11,\"clienteID\":960,\"recorridoID\":1,\"domicilio\":\"4499 Rockefeller Point\",\"nombre\":\"Vyky Bodiam\",\"latitud\":-31.350872,\"longitud\":-64.335438,\"telefono\":\"(351)-155-6290\",\"productoID\":null,\"producto\":null,\"envasesDevueltos\":null,\"envasesPrestados\":null,\"precioUnitario\":null},{\"ventaID\":12,\"clienteID\":965,\"recorridoID\":1,\"domicilio\":\"75715 Glendale Alley\",\"nombre\":\"Lavinie Martinuzzi\",\"latitud\":-31.3419,\"longitud\":-64.350444,\"telefono\":\"(351)-636-7394\",\"productoID\":null,\"producto\":null,\"envasesDevueltos\":null,\"envasesPrestados\":null,\"precioUnitario\":null},{\"ventaID\":13,\"clienteID\":335,\"recorridoID\":1,\"domicilio\":\"51 American Ash Street\",\"nombre\":\"Nicholle Willetts\",\"latitud\":-31.332114,\"longitud\":-64.333717,\"telefono\":\"(351)-340-3226\",\"productoID\":null,\"producto\":null,\"envasesDevueltos\":null,\"envasesPrestados\":null,\"precioUnitario\":null},{\"ventaID\":14,\"clienteID\":236,\"recorridoID\":1,\"domicilio\":\"433 Ramsey Hill\",\"nombre\":\"Siobhan Gittins\",\"latitud\":-31.364936,\"longitud\":-64.344629,\"telefono\":\"(351)-537-7877\",\"productoID\":null,\"producto\":null,\"envasesDevueltos\":null,\"envasesPrestados\":null,\"precioUnitario\":null},{\"ventaID\":15,\"clienteID\":78,\"recorridoID\":1,\"domicilio\":\"95253 Dovetail Road\",\"nombre\":\"Jessalin M'Chirrie\",\"latitud\":-31.362337,\"longitud\":-64.349992,\"telefono\":\"(351)-226-4763\",\"productoID\":null,\"producto\":null,\"envasesDevueltos\":null,\"envasesPrestados\":null,\"precioUnitario\":null}]");
//        } catch (JSONException e) {
//            System.out.println("ERROR AL PROCESAR JSON");
//            e.printStackTrace();
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//
//        mViewPager = (ViewPager) findViewById(R.id.recorridoViewPager);
//        VentaPagerAdapter mAdapter = new VentaPagerAdapter(this, getSupportFragmentManager(), results);
//        mViewPager.setAdapter(mAdapter);
//
//        tabLayout.setupWithViewPager(mViewPager);
//        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        /*mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));*/
        // -----------------------------------------------------------------------------------------

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void updateJsonData(Recorrido recorrido) {
        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.recorridoViewPager);
        mAdapter = new VentaPagerAdapter(this, getSupportFragmentManager(), recorrido);
        mViewPager.setAdapter(mAdapter);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        mViewPager.setCurrentItem(5, true);
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //getLocation();
                } else {
                    System.out.println("permission denied!");
                }
                break;
        }
    }

    private void getLocation() {
        MyTracker tracker = new MyTracker(this);
        latitud = tracker.getLatitude();
        longitud = tracker.getLongitude();

        coordinates = Uri.parse("geo:" + latitud + "," + longitud);
        Log.v("COORDINATES", latitud + "," + longitud);

        int idx = this.mAdapter.getIndexClosestToLocation(latitud, longitud);
        this.mViewPager.setCurrentItem(idx);
    }


    private class RetrieveRecorrido extends AsyncTask<String, Void, Recorrido> {

        @Override
        protected Recorrido doInBackground(String... urls) {
            ArrayList<Cliente> clientes = new ArrayList<>();
            Recorrido recorrido = null;
            int numeroRecorrido = 1; // TODO: OBTENER DE LA APP
            String BASE_URL = "http://192.168.0.16:3000/api/"; // TODO: VER EN DONDE PONER...
            Gson gson = new Gson();

            try {
                // GET AND BUILD RECORRIDO ---------------------------------------------------------
                URL url = new URL(BASE_URL + "recorrido/" + numeroRecorrido);
                String jsonRecorridoResponse = makeHttpRequest(url);
                Log.d("Response: ", jsonRecorridoResponse);
                recorrido = gson.fromJson(jsonRecorridoResponse, Recorrido.class);

                // GET AND BUILD CLIENTES ARRAYLIST ------------------------------------------------
                URL url2 = new URL(BASE_URL + "clientes/" + numeroRecorrido);
                String jsonClientesResponse = makeHttpRequest(url2);
                Log.d("Response: ", jsonClientesResponse);

                JSONArray jsonClientes = new JSONArray(jsonClientesResponse);
                for (int i = 0; i < jsonClientes.length(); i++) {
                    Cliente cliente = gson.fromJson(jsonClientes.getJSONObject(i).toString(), Cliente.class);
                    clientes.add(cliente);
                }
                recorrido.buildVentas(clientes);

                Producto agua = new Producto(1, "Agua 20 Litros", 200, 50);
                Producto soda = new Producto(1, "Soda 1 Litro", 1000, 10);
                for (Venta venta : recorrido.getVentas()) {
                    Map<Producto, Integer> values = new HashMap<>();

                    Integer cantidadAgua = (int) Math.ceil(Math.random() * 3);
                    Integer cantidadSoda = (int) Math.ceil(Math.random() * 7);

                    values.put(soda, cantidadSoda);
                    values.put(agua, cantidadAgua);

                    venta.buildDetalleVentas(values);
                }


            } catch (Exception e) {
                Log.e("ERROR EN HTTP GET", e.toString());
            }

            String resultJson = gson.toJson(recorrido);

            return recorrido;
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
                // TODO: Handle the exception
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
            // TODO: check this.exception
            // TODO: do something with the feed
            updateJsonData(feed);
            getLocation();
        }
    }
}


