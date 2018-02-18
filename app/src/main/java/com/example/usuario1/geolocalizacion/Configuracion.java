package com.example.usuario1.geolocalizacion;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.commons.io.TaggedIOException;

import java.util.ArrayList;

public class Configuracion extends AppCompatActivity {
    ArrayList <Vehiculo> lista_vehiculos;
    ArrayAdapter<String> adapter;
    ListView lista;
    Button btn_guardar;
    ArrayList<String> items = new ArrayList();
    TextView txt_uno,txt_dos;
    EditText marca,modelo,consumo,combustible;
    int id;
    ManejoBD bd;
    SQLiteDatabase baseDatos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);
        lista = (ListView)findViewById(R.id.lista_vehiculos);
        lista_vehiculos = new ArrayList<Vehiculo>();
        marca = (EditText)findViewById(R.id.marca);
        modelo = (EditText)findViewById(R.id.modelo);
        consumo = (EditText)findViewById(R.id.consumo);
        combustible = (EditText)findViewById(R.id.combustible);
        btn_guardar = (Button)findViewById(R.id.bGuardar);


        baseDatos = this.openOrCreateDatabase("BDGeolocalizacion", MODE_PRIVATE, null);
        bd = new ManejoBD();

                lista_vehiculos = (ArrayList<Vehiculo>) getIntent().getSerializableExtra("lista");
        //int x = bd.obtenerIdVehiculo();
       // Log.e("ID",String.valueOf(x));
         for (int i = 0; i < lista_vehiculos.size() ; i++) {

             items.add(lista_vehiculos.get(i).getMarca()+" " +lista_vehiculos.get(i).getModelo()+ "     consumo: "
                     +lista_vehiculos.get(i).getConsumo()+" l/100km");
             id++;
         }

       adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, items);





        lista.setAdapter(adapter);

        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int x = id +1;
                Log.e("Valor ID",String.valueOf(x));
                try {
                    Vehiculo vehiculo = new Vehiculo(x,marca.getText().toString(),modelo.getText().toString(),
                            Double.parseDouble(consumo.getText().toString()),combustible.getText().toString());


                    String sql = "insert into Vehiculos values (" + vehiculo.getId() + ",'" + vehiculo.getMarca() +
                            "','" + vehiculo.getModelo() + "'," + vehiculo.getConsumo() + ",'" + vehiculo.getCombustible() +  "');";
                    baseDatos.execSQL(sql);



                   // baseDatos.insertarVehiculo(vehiculo);
                    Log.e("BIEEEEEN",String.valueOf(x));
                }catch (Exception e){
                    Log.e("ERROR En INSERTAR","ERROOOOOOOOR");
                }


                /*Intent intent  = new Intent(Configuracion.this,MainActivity.class);
                intent.putExtra("objeto",vehiculo);
                setResult(RESULT_OK,intent);
                finish();*/



            }
        });
    }
}
