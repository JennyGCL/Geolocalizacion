package com.example.usuario1.geolocalizacion;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Crist on 29/01/2018.
 */

public class DescargarArchivo extends AsyncTask<String, Void, String> {
    private String resultado = "";
    private int espera = 0;

    public String getResultado() {
        return resultado;
    }

    public int getEspera() {
        return espera;
    }

    @Override
    protected void onPreExecute(){
    }
    @Override
    protected String doInBackground(String... urls) {
        String datos = "";
        URL url;
        HttpURLConnection urlConnection = null;
        try {
            // Obtengo la URL a descargar
            url = new URL(urls[0]);
            // Abro una conexión con esa URL
            urlConnection = (HttpURLConnection)url.openConnection();
            // Obtengo los datos de la página
            InputStream inputStream = urlConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new
                    InputStreamReader(inputStream));
            while((datos = bufferedReader.readLine()) != null){
                resultado = resultado + datos +"\n";
            }

            // Cierro las conexiones
            urlConnection.disconnect();
            inputStream.close();
            bufferedReader.close();
            espera++;
            return resultado;
        }
        catch (Exception e) {
            urlConnection.disconnect();
            e.printStackTrace();
            return resultado;
        }
    }
    @Override
    protected void onPostExecute(String s) {
    }
}

