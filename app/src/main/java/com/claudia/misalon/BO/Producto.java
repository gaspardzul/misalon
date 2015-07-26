package com.claudia.misalon.BO;

import com.orm.SugarRecord;

/**
 * Created by gaspar on 26/07/15.
 */
public class Producto extends SugarRecord<Producto>{

    private String nombre;
    private double precio;
    private int tipo; // es producto o servicio


    public Producto(){}

    public Producto(String nombre, double precio, int tipo){
        this.setNombre(nombre);
        this.setPrecio(precio);
        this.setTipo(tipo);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
}
