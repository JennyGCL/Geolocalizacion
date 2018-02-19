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
    private double consumo;
    private String combustible = "";
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
        pasajeros.setText("1");
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
                //intent.putExtra("lista",lista_vehiculos );
                startActivityForResult(intent, 0);


            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 0) {

            if (resultCode == RESULT_OK) {
                combustible = data.getStringExtra("combustible");
                consumo = data.getDoubleExtra("consumo", 5);
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void iniciarRuta(View v){
        boolean valido = true;
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




        //Enviamos el consumo y el precio del combustible
        intent.putExtra("consumo", consumo);


        if(combustible.equals("")){
            valido = false;
        }else{
            valido = true;
            if(combustible.equals("Gasolina")){
                intent.putExtra("combustible", precioGasolina);
            }else if(combustible.equals("Gasoleo")){
                intent.putExtra("combustible", precioGasoleo);
            }else{
                intent.putExtra("combustible", precioGasoleoPlus);
            }
        }

        if (valido){
            startActivity(intent);
        }else{
            Toast.makeText(this,"Debe seleccionar un vehiculo en la opcion de configurar vehiculo", Toast.LENGTH_LONG).show();
        }
    }
}