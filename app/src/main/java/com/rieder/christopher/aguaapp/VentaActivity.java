package com.rieder.christopher.aguaapp;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.net.Uri;
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
import com.rieder.christopher.aguaapp.domain.Cliente;
import com.rieder.christopher.aguaapp.domain.EnvasesEnComodato;
import com.rieder.christopher.aguaapp.domain.Producto;
import com.rieder.christopher.aguaapp.domain.Recorrido;
import com.rieder.christopher.aguaapp.domain.ServerResponse;
import com.rieder.christopher.aguaapp.domain.TemplateRecorrido;
import com.rieder.christopher.aguaapp.domain.Venta;

import org.ankit.gpslibrary.MyTracker;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VentaActivity extends AppCompatActivity implements IPayload, RecorridoFragment.OnVentaClickListener {

    // URL: https://agua-lacalera.herokuapp.com/;
    // URL2: https://192.168.0.2:3000/;
    private Recorrido recorrido;
    private File file;
    private VentaPagerAdapter mAdapter;
    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private ArrayList<Producto> productos;

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
    Callback<ArrayList<EnvasesEnComodato>> envasesCallback = new Callback<ArrayList<EnvasesEnComodato>>() {
        /* When server response. */
        @Override
        public void onResponse(Call<ArrayList<EnvasesEnComodato>> call, Response<ArrayList<EnvasesEnComodato>> response) {
            StringBuffer messageBuffer = new StringBuffer();

            int statusCode = response.code();

            if (statusCode == 200) {
                updateData(response.body());

                messageBuffer.append("RECIBIDO!");
            } else {
                // If server return error.
                messageBuffer.append("Server return error code is ");
                messageBuffer.append(statusCode);
                messageBuffer.append("\r\n\r\n");
                messageBuffer.append("Error message is ");
                messageBuffer.append(response.message());
            }

            // Show a Toast message.
            Toast.makeText(getApplicationContext(), messageBuffer.toString(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onFailure(Call<ArrayList<EnvasesEnComodato>> call, Throwable t) {
            Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

    // BUILD RECORRIDOS...
    @Override
    public void payloadClientes(TemplateRecorrido template, ArrayList<Producto> productos) {

        @SuppressLint("SimpleDateFormat")
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String today = dateFormat.format(new Date());

        this.productos = productos;
        this.recorrido = new Recorrido(template.getNombre(), today, 1, 0, 60);
        recorrido.buildVentas(template.getClientes());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIservice service = retrofit.create(APIservice.class);
        service.getEnvasesEnComodato(template.getRecorridoTemplateID()).enqueue(envasesCallback);
        // GET ENVASES EN COMODATO DE CADA CLIENTE.
//        new RetrieveRecorrido(this, template.getRecorridoTemplateID()).execute();
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
        } else if (id == R.id.menu_add_cliente) {
            Cliente c = new Cliente(3234, "TOTO", "LAS HERAS 933", "333", 22, 33);
            Venta v = new Venta(c);
            Map<Producto, Integer> values = new HashMap<>();

            values.put(productos.get(0), 0);
            values.put(productos.get(1), 0);
            v.buildDetalleVentas(values);

            this.recorrido.getVentas().add(v);

            mAdapter.notifyDataSetChanged();
        } else if (id == R.id.menu_post_data) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.0.2:3000/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            retrofit2.Callback<ServerResponse> callback = new Callback<ServerResponse>() {

                /* When server response. */
                @Override
                public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {

                    StringBuffer messageBuffer = new StringBuffer();
                    int statusCode = response.code();
                    if (statusCode == 200) {
                        // Get return string.
                        ServerResponse returnBodyText = response.body();
                        messageBuffer.append(returnBodyText.getMessage());
                    } else {
                        // If server return error.
                        messageBuffer.append("Server return error code is ");
                        messageBuffer.append(statusCode);
                        messageBuffer.append("\r\n\r\n");
                        messageBuffer.append("Error message is ");
                        messageBuffer.append(response.message());
                    }

                    // Show a Toast message.
                    Toast.makeText(getApplicationContext(), messageBuffer.toString(), Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<ServerResponse> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            };

            APIservice service = retrofit.create(APIservice.class);
            service.recorrido(recorrido).enqueue(callback);
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

    @Override
    public void onVentaSelected(int position) {
        mAdapter.setCurrIndex(position);
        mAdapter.notifyDataSetChanged();
        mViewPager.setCurrentItem(VentaPagerAdapter.TAB_INDEX_VENTA);
    }

    private void updateData(ArrayList<EnvasesEnComodato> envasesEnComodato) {
        // Iterar cada venta, si coincide el clienteID, agregar cantidades de productos.
        // O(n^2) but who cares, son pocas iteraciones, no hace falta usar árboles binarios, etc.
        for (Venta venta : recorrido.getVentas()) {
            Map<Producto, Integer> values = new HashMap<>();

            int clienteID = venta.getCliente().getClienteID();
            for (EnvasesEnComodato e : envasesEnComodato) {
                if (e.getClienteID() == clienteID) {
                    Log.i("Envases", productos.get(e.getProductoID() - 1) + "|" + e.getCantidad());
                    //TODO: ESTA BIEN HECHO? TESTEAR LUEGO...
                    //Tal vez, implementar método equals.
                    values.put(productos.get(e.getProductoID() - 1), e.getCantidad());
                }
            }
            venta.buildDetalleVentas(values);
        }

        this.mViewPager = this.findViewById(R.id.venta_view_pager);
        this.mAdapter = new VentaPagerAdapter(this, this.getSupportFragmentManager(), this.recorrido);
        this.mViewPager.setAdapter(this.mAdapter);

        this.tabLayout = this.findViewById(R.id.venta_tabs);
        this.tabLayout.setupWithViewPager(this.mViewPager);
        this.tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }
}
