package com.example.usuario1.geolocalizacion;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    String datos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Descargamos los precios de los carburantes
        DescargarArchivo da = new DescargarArchivo();
        da.execute("http://www.precioscarburantes.com/");
        while(da.getEspera()==0){

        }

        //Recogemos datos y los procesamos, sacando la URL de la bandera y el nombre del país
        datos = da.getResultado();
        String[] lines = datos.split(System.getProperty("line.separator"));
        String resultado = null;
        String[] lines2 = null;
        int i = 0, cont = -1;
        while(i<lines.length){

            if(lines[i].contains("Precio gasolina")){
                System.out.println(lines[i]);
                resultado = lines[i];
            }

            lines2 = resultado.split("<td>");
            for (int b=0; b < lines2.length;b++){
                System.out.println(lines2[b]);
            }
            //Pattern p = Pattern.compile("<strong>");
            //Matcher m = p.matcher(lines[i].trim());
            //if (m.find()){
               // String bandera = (lines[i+1].trim().substring(14, ((lines[i+1].trim().length())-27)));
               // String nombrePais = (lines[i+2].trim().substring(23, ((lines[i+2].trim().length())-5)));
               // cont++;
            //}
            i++;
        }
    }
}
