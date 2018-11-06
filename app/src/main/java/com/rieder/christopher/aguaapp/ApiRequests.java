package com.rieder.christopher.aguaapp;

import android.os.AsyncTask;
import android.util.Log;

import com.rieder.christopher.aguaapp.DomainClasses.Recorrido;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

// por un lado, mucho de la clase se mantiene
// por otro lado, tengo que crear distintas clases depsues del retriebe
// y recien luego de eso actualizar la UI.

class ApiRequests extends AsyncTask<String, Void, Recorrido> {

    private Recorrido recorrido;

    @Override
    protected Recorrido doInBackground(String... urls) {
        Recorrido r = new Recorrido("");
        String jsonRecorrido = "";
        String jsonClientes = "";

        try {
            URL url = new URL("http://192.168.0.16:3000/api/fullRecorrido/2");
            jsonRecorrido = makeHttpRequest(url);
            Log.d("Response: ", jsonRecorrido);
            r = new Recorrido(jsonRecorrido);
        } catch (Exception e) {
            Log.e("ERROR EN HTTP GET", e.toString());
        }

        this.recorrido = r;
        return r;
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
        //updateJsonData(recorrido.getTest());
        //getLocation();
    }
}