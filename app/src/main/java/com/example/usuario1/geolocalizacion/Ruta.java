package com.example.usuario1.geolocalizacion;

/**
 * Created by Vladimir on 17/02/2018.
 */

public class Ruta {
    private int id;
    private int idVehiculo;
    private String origen;
    private String destino;
    private int km;
    private double lcombustible;
    private double precio;
    private String tiempo;

    public Ruta() {

    }

    public Ruta(int id, int idVehiculo, String origen, String destino, int km, double lcombustible, double precio, String tiempo) {
        this.id = id;
        this.idVehiculo = idVehiculo;
        this.origen = origen;
        this.destino = destino;
        this.km = km;
        this.lcombustible = lcombustible;
        this.precio = precio;
        this.tiempo = tiempo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(int idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public int getKm() {
        return km;
    }

    public void setKm(int km) {
        this.km = km;
    }

    public double getLcombustible() {
        return lcombustible;
    }

    public void setLcombustible(double lcombustible) {
        this.lcombustible = lcombustible;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }
}
