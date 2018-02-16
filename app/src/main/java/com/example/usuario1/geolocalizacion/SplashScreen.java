package com.example.usuario1.geolocalizacion;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.felipecsl.gifimageview.library.GifImageView;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

public class SplashScreen extends AppCompatActivity {

    private GifImageView gifImageView;
    private ProgressBar progressBar;
    String datos, precioGasolina, precioGasoleo, precioGasoleoPlus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animacion);

        gifImageView = (GifImageView)findViewById(R.id.gifImageView);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        progressBar.setVisibility(progressBar.VISIBLE);

        //Set GIFImageView resource
        try{
            InputStream inputStream = getAssets().open("icon-2-fixed.gif");
            byte[] bytes = IOUtils.toByteArray(inputStream);
            gifImageView.setBytes(bytes);
            gifImageView.startAnimation();
        }
        catch (IOException ex)
        {

        }

        //Wait for 3 seconds and start Activity Main
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

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
                        resultado = lines[i];
                    }
                    i++;
                }
                lines2 = resultado.split("\\<td\\>");
                for (int b=0; b < lines2.length;b++){
                    System.out.println(lines2[b]);
                    if(lines2[b].contains("</strong> 95</td>")){
                        precioGasolina = lines2[b+1].substring(0,5);
                    }else if(lines2[b].contains("</strong> A</td>")){
                        precioGasoleo = lines2[b+1].substring(0,5);
                    }else if(lines2[b].contains("</strong> A+</td>")){
                        precioGasoleoPlus = lines2[b+1].substring(0,5);
                    }
                }

                System.out.println("Precio gasolina: "+precioGasolina+", Precio Gasoleo A: "+precioGasoleo+", Precio Gasoleo A+: "+precioGasoleoPlus);

                SplashScreen.this.startActivity(new Intent(SplashScreen.this,MainActivity.class));




                SplashScreen.this.finish();
            }
        },10000); // 3000 = 3seconds
    }
}
