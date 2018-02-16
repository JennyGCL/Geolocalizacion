package com.example.usuario1.geolocalizacion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    String datos, precioGasolina, precioGasoleo, precioGasoleoPlus;

    Button btn_iniciar, btn_configuarar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_iniciar = (Button) findViewById(R.id.btn_iniciar);
        btn_configuarar = (Button) findViewById(R.id.btn_configurar);


    }

    public void OnClickConfiguarar (View view) {
        Intent intent = new Intent(this, Configuracion.class);
        startActivity(intent);

    }

    public void iniciarRuta(View v){
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
}
