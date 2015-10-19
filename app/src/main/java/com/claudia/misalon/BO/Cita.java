package com.claudia.misalon.BO;



import java.util.ArrayList;
import java.util.Date;

/**
 * Created by gaspar on 26/07/15.
 */
public class Cita{

    String nombre;
    Date fecha; //fecha y hora
    ArrayList<Producto> servicio;
    double precio;
    Cliente cliente;

    public Cita(){}

    public Cita(String nombre,Date fecha,ArrayList<Producto> servicios, double precio, Cliente cliente){
        this.nombre = nombre;
        this.fecha = fecha;
        this.servicio = servicios;
        this.cliente = cliente;
    }

}
