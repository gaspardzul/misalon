package com.claudia.misalon.BO;

import com.orm.SugarRecord;

/**
 * Created by gaspar on 26/07/15.
 */
public class Producto extends SugarRecord<Producto>{

    String nombre;
    double precio;
    int tipo; // es producto o servicio


    public Producto(){}

    public Producto(String nombre, double precio, int tipo){
        this.nombre = nombre;
        this.precio =precio;
        this.tipo = tipo;
    }
}
