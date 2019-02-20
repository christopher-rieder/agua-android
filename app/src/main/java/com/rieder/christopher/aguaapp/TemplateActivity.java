package com.rieder.christopher.aguaapp;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.jinais.gnlib.android.launcher.GNLauncher;
import com.rieder.christopher.aguaapp.domain.Producto;
import com.rieder.christopher.aguaapp.domain.TemplateRecorrido;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TemplateActivity extends AppCompatActivity {

    private TemplatePagerAdapter mAdapter;
    private ViewPager mViewPager;
    private ArrayList<TemplateRecorrido> templates; // As an array because is less code for Gson
    Callback<ArrayList<TemplateRecorrido>> templatesCallback = new Callback<ArrayList<TemplateRecorrido>>() {
        /* When server response. */
        @Override
        public void onResponse(Call<ArrayList<TemplateRecorrido>> call, Response<ArrayList<TemplateRecorrido>> response) {
            StringBuffer messageBuffer = new StringBuffer();

            int statusCode = response.code();

            if (statusCode == 200) {
                templates = response.body();
                setData();

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
        public void onFailure(Call<ArrayList<TemplateRecorrido>> call, Throwable t) {
            Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
        }
    };
    private TabLayout tabLayout;
    private ArrayList<Producto> productos;
    Callback<ArrayList<Producto>> productosCallback = new Callback<ArrayList<Producto>>() {
        /* When server response. */
        @Override
        public void onResponse(Call<ArrayList<Producto>> call, Response<ArrayList<Producto>> response) {
            StringBuffer messageBuffer = new StringBuffer();

            int statusCode = response.code();

            if (statusCode == 200) {
                productos = response.body();

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
        public void onFailure(Call<ArrayList<Producto>> call, Throwable t) {
            Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIservice service = retrofit.create(APIservice.class);
        service.getTemplateRecorridos().enqueue(templatesCallback);
        service.getProductos().enqueue(productosCallback);

        final Button seleccionarRecorridoBtn = findViewById(R.id.seleccionar_recorrido_button);

        // Usando la libreria gnlib-android para enviar datos entre activities
        seleccionarRecorridoBtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                GNLauncher launcher = GNLauncher.get();
                IPayload proxy = (IPayload) launcher.getProxy(seleccionarRecorridoBtn.getContext(), IPayload.class, VentaActivity.class);
                TemplateRecorrido currTemplate = templates.get(mViewPager.getCurrentItem());

                proxy.payloadClientes(currTemplate, productos);
                return true;
            }
        });
    }

    void setData() {
        this.mViewPager = this.findViewById(R.id.template_view_pager);
        this.mAdapter = new TemplatePagerAdapter(this, this.getSupportFragmentManager(), this.templates);
        this.mViewPager.setAdapter(this.mAdapter);

        this.tabLayout = this.findViewById(R.id.template_tabs);
        this.tabLayout.setupWithViewPager(this.mViewPager);
        this.tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }
}
