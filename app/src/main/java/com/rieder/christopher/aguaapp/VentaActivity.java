package com.rieder.christopher.aguaapp;

import android.annotation.SuppressLint;
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
import com.rieder.christopher.aguaapp.DomainClasses.Producto;
import com.rieder.christopher.aguaapp.DomainClasses.Recorrido;
import com.rieder.christopher.aguaapp.DomainClasses.TemplateRecorrido;
import com.rieder.christopher.aguaapp.DomainClasses.Venta;

import org.ankit.gpslibrary.MyTracker;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class VentaActivity extends AppCompatActivity implements IPayload {

    private Recorrido recorrido;
    private File file;
    private VentaPagerAdapter mAdapter;
    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private Producto[] productos;

    private static final int REQUEST_CODE_PERMISSION = 2;

    // TODO: Implementar Activity LifeCycle Methods

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venta);

        FloatingActionButton fab2 = findViewById(R.id.venta_fab2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VentaActivity.this.getLocation();
            }
        });

        // Notificar que la actividad está lista para recibir el payload (List de clientes)
        GNLauncher.get().ping(this);
    }
    // TODO: PERMITIR AGREGAR O QUITAR CLIENTES. *LUEGO* DE CARGAR LOS DEL TEMPLATE.
    // ME PARECE QUE VA A SER MEJOR HACERLO EN VentaActivity.
    // Y TENER UN FRAGMENT, ACTIVITY O LO QUE SEA PARA AGREGAR O QUITAR CLIENTES.


    // BUILD RECORRIDOS...
    @Override
    public void payloadClientes(TemplateRecorrido template, Producto[] productos) {

        @SuppressLint("SimpleDateFormat")
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");

        //to convert Date to String, use format method of SimpleDateFormat class.
        String today = dateFormat.format(new Date());

        this.recorrido = new Recorrido(template.getNombre(), today, 1, 0, 60);
        recorrido.buildVentas(template.getClientes());

        // FIXME: BORRAR CUANDO ESTO SE OBTENGA DE LA DB...
        for (Venta venta : recorrido.getVentas()) {
            Map<Producto, Integer> values = new HashMap<>();

            Integer cantidadAgua = (int) Math.ceil(Math.random() * 3); //TODO: GET FROM DB
            Integer cantidadSoda = (int) Math.ceil(Math.random() * 7); //TODO: GET FROM DB

            values.put(productos[0], cantidadAgua);
            values.put(productos[1], cantidadSoda);

            venta.buildDetalleVentas(values);
        }

        // GET ENVASES EN COMODATO DE CADA CLIENTE.
        new RetrieveRecorrido(this, template.getRecorridoTemplateID()).execute();
    }

    private void updateData() {
        this.mViewPager = this.findViewById(R.id.venta_view_pager);
        this.mAdapter = new VentaPagerAdapter(this, this.getSupportFragmentManager(), this.recorrido);
        this.mViewPager.setAdapter(this.mAdapter);

        this.tabLayout = this.findViewById(R.id.venta_tabs);
        this.tabLayout.setupWithViewPager(this.mViewPager);
        this.tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    // It isn't large enough to warrant an async task
    // TODO: ESTO ES PARA DEBUG.
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

    private static class RetrieveRecorrido extends AsyncTask<String, Void, Recorrido> {

        // TODO: REMOVE LATER, THIS IS FOR TESTING PURPOSES
        private String BASE_URL = "http://192.168.0.16:3000/api/";
        private int recorridoTemplateID;
        private WeakReference<VentaActivity> activityReference;

        RetrieveRecorrido(VentaActivity context, int recorridoTemplateID) {
            activityReference = new WeakReference<>(context);
            this.recorridoTemplateID = recorridoTemplateID;
        }

        @Override
        protected void onPreExecute() {
            VentaActivity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) return;

            Toast.makeText(activity, "Preparando para cargar datos...", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Recorrido doInBackground(String... urls) {
            Recorrido newRecorrido = new Recorrido("GET FROM TEMPLATE", "TODAY", 1, 0, 60);
            try {
                URL checkLocalServer = new URL("http://192.168.0.16:3000/");
                String hello_world = HttpHelper.makeHttpRequest(checkLocalServer);

                if (!hello_world.equals("Hello World!")) {
                    BASE_URL = "http://tophercasa.ddns.net:3000/api/"; // TODO: VER EN DONDE PONER...
                }


            } catch (Exception e) {
                Log.e("ERROR EN HTTP GET", e.toString());
            }
            return newRecorrido;
        }

        protected void onPostExecute(Recorrido feed) {
            VentaActivity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) return;
            activity.updateData();
        }
    }
}
