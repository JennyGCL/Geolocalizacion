package com.example.usuario1.geolocalizacion;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sunfusheng.marqueeview.MarqueeView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private String datos, precioGasolina, precioGasoleo, precioGasoleoPlus;
    private Button btn_iniciar, btn_configuarar;
    private EditText et_origen;
    private EditText et_destino;
    private MarqueeView tv_marquesina;
    private String origen;
    private String destino;
    SQLiteDatabase baseDatos;
    ArrayList <Vehiculo>lista_vehiculos;
    ManejoBD bd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_iniciar = (Button) findViewById(R.id.btn_iniciar);
        btn_configuarar = (Button) findViewById(R.id.btn_configurar);

        et_origen = (EditText) findViewById(R.id.et_origen);
        et_origen.setText("Localizacion Actual");
        et_destino = (EditText) findViewById(R.id.et_destino);

        tv_marquesina = (MarqueeView) findViewById(R.id.tv_marquesina);

        precioGasolina = getIntent().getStringExtra("gasolina");
        precioGasoleo = getIntent().getStringExtra("gasoleo");
        precioGasoleoPlus = getIntent().getStringExtra("gasoleoPlus");

        List<String>precios= new ArrayList<String>();
        precios.add("Gasolina: "+precioGasolina+"€");
        precios.add("Gasoleo A: "+ precioGasoleo+"€");
        precios.add("Gasoleo A+: "+ precioGasoleoPlus+"€");
        tv_marquesina.startWithList(precios);

        try {
            lista_vehiculos = new ArrayList<Vehiculo>();
            baseDatos = this.openOrCreateDatabase("BDGeolocalizacion", MODE_PRIVATE, null);
             bd = new ManejoBD(baseDatos);
            bd.crearTablas();
            Vehiculo v = new Vehiculo(1,"AUDI","A5",4.6,"Diésel");
            bd.insertarVehiculo(v);
            lista_vehiculos = bd.obtenerDatosVehiculos();
            Log.e("TAMAÑO",String.valueOf(lista_vehiculos.size()));
            for (int i = 0; i < lista_vehiculos.size() ; i++) {
                Log.e("VEHICULO",lista_vehiculos.get(i).getMarca()+lista_vehiculos.get(i).getConsumo());
            }
        }catch (Exception e){

        }

    }

    public void OnClickConfiguarar (View view) {
        Intent intent = new Intent(this, Configuracion.class);
        intent.putExtra("lista",lista_vehiculos );
        startActivity(intent);


    }

    public void iniciarRuta(View v){
        origen = et_origen.getText().toString();
        destino = et_destino.getText().toString();
        Intent intent = new Intent(this, MapsActivity.class);

        intent.putExtra("origen", origen);
        intent.putExtra("destino", destino);
        startActivity(intent);


    }
}
