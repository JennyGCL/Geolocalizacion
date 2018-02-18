package com.example.usuario1.geolocalizacion;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Configuracion extends AppCompatActivity {
    ArrayList <Vehiculo> lista_vehiculos;
    ArrayAdapter<String> adapter;
    ListView lista;
    Button btn_guardar;
    ArrayList<String> items = new ArrayList();
    TextView txt_uno,txt_dos;
    EditText marca,modelo,consumo,combustible;
    int itemSeleccionado;
    int id=0;
    int x;
    SQLiteDatabase baseDatos;
    AlertDialog.Builder builder;
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

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, items);
        baseDatos = this.openOrCreateDatabase("BDGeolocalizacion", MODE_PRIVATE, null);
        lista_vehiculos = (ArrayList<Vehiculo>) getIntent().getSerializableExtra("lista");

         for (int i = 0; i < lista_vehiculos.size() ; i++) {
             items.add(lista_vehiculos.get(i).getMarca()+" " +lista_vehiculos.get(i).getModelo()+ "     consumo: "
                     +lista_vehiculos.get(i).getConsumo()+" l/100km");
             id++;
         }

        lista.setAdapter(adapter);

        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                x = id +1;
                id++;
                Log.e("Valor ID",String.valueOf(x));
                try {
                    Vehiculo vehiculo = new Vehiculo(x,marca.getText().toString(),modelo.getText().toString(),
                            Double.parseDouble(consumo.getText().toString()),combustible.getText().toString());

                    String sql = "insert into Vehiculos values (" + vehiculo.getId() + ",'" + vehiculo.getMarca() +
                            "','" + vehiculo.getModelo() + "'," + vehiculo.getConsumo() + ",'" + vehiculo.getCombustible() +  "');";
                    baseDatos.execSQL(sql);
                    lista_vehiculos.add(vehiculo);
                    items.add(lista_vehiculos.get(x - 1).getMarca()+" " +lista_vehiculos.get(x - 1).getModelo()+ "     consumo: "
                            +lista_vehiculos.get(x - 1).getConsumo()+" l/100km");

                    marca.setText("");
                    modelo.setText("");
                    consumo.setText("");
                    combustible.setText("");
                }catch (Exception e){
                    System.out.println(e.toString());
                }
                lista.setAdapter(adapter);


            }
        });
        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                itemSeleccionado = position+1;
                mostrarDialogo();
                return true;
            }
        });

    }
    private void mostrarDialogo() {
        builder = new AlertDialog.Builder(this);
        //Obliga a que se pulse sobre alguno de los botones para que se cierre el dialogo.
        builder.setCancelable(false);
        builder.setMessage("¿Quiere eliminar esta vehículo?")
                .setTitle("¿Está seguro?")
                .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int itemId) {
                                String sql = "Delete from Vehiculos where id=" + itemSeleccionado;
                                baseDatos.execSQL(sql);
                                lista_vehiculos.remove(itemSeleccionado);
                                items.remove(itemSeleccionado);
                                x--;
                                lista.setAdapter(adapter);
                                Toast.makeText(Configuracion.this, "Vehículo borrado!", Toast.LENGTH_LONG).show();
                            }
                        }
                ).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
    }
}
