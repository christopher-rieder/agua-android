package com.rieder.christopher.aguaapp;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIservice {
    @Headers({"Content-Type: application/json"})

    @POST("api/recorrido")
    Call<ResponseBody> recorrido(@Body RequestBody body);
}
