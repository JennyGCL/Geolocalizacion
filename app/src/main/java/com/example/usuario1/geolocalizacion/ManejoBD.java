package com.example.usuario1.geolocalizacion;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;


public class ManejoBD {

   private SQLiteDatabase baseDatos;
    ArrayList<Vehiculo> lista_vehiculos;
    ArrayList<Ruta> lista_rutas;
    public ManejoBD(SQLiteDatabase baseDatos) {
        this.baseDatos = baseDatos;
    }
    public ManejoBD() {

    }

    /**
     * Método que crea las tablas de la base de datos BDGeolocalización.
     * Tablas: Vehiculos, Rutas
     */
    public  void crearTablas(){

        try{
            //Sentencia sql para crear la tabla Vehiculos en caso de que no exista.
            String sqlCrearTabla = "CREATE TABLE IF NOT EXISTS Vehiculos (Id INTEGER PRIMARY KEY," +
                                        "Marca VARCHAR(50), Modelo VARCHAR(200), Consumo REAL(3,1), Carburante VARCHAR(50));";
            //Ejecuta la sentencia.
            baseDatos.execSQL(sqlCrearTabla);

            //Sentencia sql para crear la tabla Rutas en caso de que no exista.
            sqlCrearTabla =  "CREATE TABLE IF NOT EXISTS Rutas (Id INTEGER PRIMARY KEY," +
                    "IdVehiculo INTEGER,Origen VARCHAR(200), Destino VARCHAR(200), Km INTEGER, LitrosCombustible REAL(5,1),Precio REAL(5,3)," +
                    "Tiempo VARCHAR(100), FOREIGN KEY(IdVehiculo) REFERENCES Vehiculos(Id));";
            //Ejecuta la sentencia.
            baseDatos.execSQL(sqlCrearTabla);
        }catch(Exception e){
            Log.e("Error",e.toString());
        }
    }

    /**
     * Método que recorre la tabla Vehiculos y almacena los datos en una lista.
     * @return Retorna una lista con objetos Vehiculo.
     */
    public ArrayList obtenerDatosVehiculos(){

        lista_vehiculos = new ArrayList<Vehiculo>();
        int id;
        String marca,modelo,combustible;
        double consumo;
        //Cursor con todos los registros de la tabla vehiculos
        Cursor c = baseDatos.rawQuery(" SELECT * FROM Vehiculos;", null);

        //Comprueba que hay mínimo un registro en el cursor para ser leido
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                id =c.getInt(0);
                marca=c.getString(1);
                modelo=c.getString(2);
                consumo=c.getDouble(3);
                combustible=c.getString(4);

                Vehiculo vehiculo = new Vehiculo(id,marca,modelo,consumo,combustible);
                lista_vehiculos.add(vehiculo);

            } while(c.moveToNext());

        }
        c.close();
        return lista_vehiculos;
    }

    /**
     * Método que recorre la tabla Rutas y almacena los datos en una lista.
     * @return Retorna una lista con objetos Ruta.
     */
    public ArrayList obtenerDatosRuta(){
        lista_rutas = new ArrayList<Ruta>();
        int id,idVehiculo,km;
        String origen,destino,tiempo;
        double lcombustible,precio;

        //Cursor con todos los registros de la tabla Rutas
        Cursor c = baseDatos.rawQuery(" SELECT * FROM Rutas", null);

        //Comprueba que hay mínimo un registro en el cursor para ser leido
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                id =c.getInt(0);
                idVehiculo=c.getInt(1);
                origen=c.getString(2);
                destino=c.getString(3);
                km=c.getInt(4);
                lcombustible = c.getDouble(5);
                precio = c.getDouble(6);
                tiempo = c.getString(7);

                Ruta ruta = new Ruta(id,idVehiculo,origen,destino,km,lcombustible,precio,tiempo);
                lista_rutas.add(ruta);
            } while(c.moveToNext());

        }
        return lista_rutas;
    }

    /**
     * Método al que se le pasa por parámetro un Vehiculo para ser insertado en la base de datos.
     * @param vehiculo
     */
    public void insertarVehiculo(Vehiculo vehiculo){
        try {

            String sql = "insert into Vehiculos values (" + vehiculo.getId() + ",'" + vehiculo.getMarca() +
                    "','" + vehiculo.getModelo() + "'," + vehiculo.getConsumo() + ",'" + vehiculo.getCombustible() +  "');";
            baseDatos.execSQL(sql);
        }catch(Exception e){

        }

    }

    /**
     * Método al que se le pasa por parámetro una Ruta para ser insertada en la base de datos.
     * @param ruta
     */
    public void insertarRuta(Ruta ruta){
        try {

            String sql = "insert into Rutas values (" + ruta.getId() + "," + ruta.getIdVehiculo() +
                    ",'" + ruta.getOrigen() + "','" + ruta.getDestino() + "',"  + ruta.getKm() + ","
                    + ruta.getLcombustible() + ","  + ruta.getPrecio() + ",'"+ ruta.getTiempo() +  "');";
            baseDatos.execSQL(sql);
        }catch(Exception e){

        }

    }

    /**
     * Obtiene el último Id de la tabla Vehiculos.
     * @return
     */
    public int obtenerIdVehiculo(){
        int id = 0;
        //Cursor con todos los registros de la tabla Rutas
        Cursor c = baseDatos.rawQuery(" SELECT * FROM Vehiculos", null);

        //Comprueba que hay mínimo un registro en el cursor para ser leido
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                id =c.getInt(0);
            } while(c.moveToNext());
        }
        return id;
    }

    /**
     * Obtiene el último Id de la tabla Rutas.
     * @return
     */
    public int obtenerIdRuta(){
        int id = 0;
        //Cursor con todos los registros de la tabla Rutas
        Cursor c = baseDatos.rawQuery(" SELECT * FROM Rutas", null);

        //Comprueba que hay mínimo un registro en el cursor para ser leido
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                id =c.getInt(0);
            } while(c.moveToNext());
        }
        return id;
    }
}
