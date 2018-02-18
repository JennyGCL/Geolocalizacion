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
    SQLiteDatabase baseDatos;
    ArrayList <Vehiculo>lista_vehiculos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_iniciar = (Button) findViewById(R.id.btn_iniciar);
        btn_configuarar = (Button) findViewById(R.id.btn_configurar);

        et_origen = (EditText) findViewById(R.id.et_origen);
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
            ManejoBD bd = new ManejoBD(baseDatos);
            bd.crearTablas();
            /*lista_vehiculos = bd.obtenerDatosVehiculos();
            for (int i = 0; i < lista_vehiculos.size() ; i++) {
                Log.e("VEHICULO",lista_vehiculos.get(i).getMarca()+lista_vehiculos.get(i).getConsumo());
            }*/
        }catch (Exception e){

        }
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
