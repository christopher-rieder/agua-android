package com.rieder.christopher.aguaapp;

import com.rieder.christopher.aguaapp.DomainClasses.EnvasesEnComodato;
import com.rieder.christopher.aguaapp.DomainClasses.Producto;
import com.rieder.christopher.aguaapp.DomainClasses.Recorrido;
import com.rieder.christopher.aguaapp.DomainClasses.ServerResponse;
import com.rieder.christopher.aguaapp.DomainClasses.TemplateRecorrido;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIservice {
    @Headers({"Content-Type: application/json"})

    @POST("api/recorrido")
    Call<ServerResponse> recorrido(@Body Recorrido body);

    @GET("api/recorridoTemplates")
    Call<ArrayList<TemplateRecorrido>> getTemplateRecorridos();


    @GET("api/productos")
    Call<ArrayList<Producto>> getProductos();

    @GET("api/envasesEnComodatoPorRecorrido/{templateId}")
    Call<ArrayList<EnvasesEnComodato>> getEnvasesEnComodato(@Path("templateId") Integer id);


}
