package com.example.usuario1.geolocalizacion;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.sunfusheng.marqueeview.MarqueeView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

    private CheckBox checkAireSi;
    private CheckBox checkEquipajeSi;
    private CheckBox checkLucesSi;
    private CheckBox checkIdaVuelta;
    private EditText pasajeros;
    private int numPasajeros;

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

        checkIdaVuelta = findViewById(R.id.checkIdaVuelta);
        checkAireSi = findViewById(R.id.check_aire);
        checkEquipajeSi = findViewById(R.id.check_equipaje);
        checkLucesSi = findViewById(R.id.check_luces);
        pasajeros = findViewById(R.id.txt_numPasajeros);

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
            //Log.e("TAMAÑO",String.valueOf(lista_vehiculos.size()));
            //for (int i = 0; i < lista_vehiculos.size() ; i++) {
               // Log.e("VEHICULO",lista_vehiculos.get(i).getMarca()+lista_vehiculos.get(i).getConsumo());
            //}
        }catch (Exception e){

        }
        btn_configuarar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, Configuracion.class);
                intent.putExtra("lista",lista_vehiculos );
                startActivity(intent);


            }
        });

    }

    public void onClickConfigurar(View view) {
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


        if(checkAireSi.isChecked()){
            intent.putExtra("aire", true);
        }else{
            intent.putExtra("aire", false);
        }


        if(checkLucesSi.isChecked()){
            intent.putExtra("luces", true);
        }else{
            intent.putExtra("luces", false);
        }

        if(checkEquipajeSi.isChecked()){
            intent.putExtra("equipaje", true);
        }else{
            intent.putExtra("equipaje", false);
        }

        if(checkIdaVuelta.isChecked()){
            intent.putExtra("idayvuelta", true);
        }else{
            intent.putExtra("idayvuelta", false);
        }

        try{
            numPasajeros = Integer.parseInt(pasajeros.getText().toString());
            intent.putExtra("numPasajeros", numPasajeros);
        }catch (Exception ex){
            Toast.makeText(this, "El numero de pasajeros debe ser numerico", Toast.LENGTH_SHORT).show();
        }



        startActivity(intent);
    }
}