package com.example.usuario1.geolocalizacion;

import java.io.Serializable;

/**
 * Created by Vladimir on 17/02/2018.
 */

public class Vehiculo implements Serializable{
    private int id;
    private String marca;
    private String modelo;
    private double consumo;
    private String combustible;

    public Vehiculo() {

    }

    public Vehiculo(int id, String marca, String modelo, double consumo, String combustible) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.consumo = consumo;
        this.combustible = combustible;
    }
    public Vehiculo( String marca, String modelo, double consumo, String combustible) {

        this.marca = marca;
        this.modelo = modelo;
        this.consumo = consumo;
        this.combustible = combustible;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public double getConsumo() {
        return consumo;
    }

    public void setConsumo(double consumo) {
        this.consumo = consumo;
    }

    public String getCombustible() {
        return combustible;
    }

    public void setCombustible(String combustible) {
        this.combustible = combustible;
    }
}
