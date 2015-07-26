package com.claudia.misalon.BO;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by gaspar on 26/07/15.
 * son los servicios que se hacen los clientes
 */

public class Servicio {

    Cliente cliente;
    ArrayList<Producto> servicios;
    Date fecha;
    double costo;

    public Servicio(){}

    public Servicio(Cliente cliente, ArrayList<Producto> servicios, Date fecha, double costo){
        this.cliente=cliente;
        this.servicios=servicios;
        this.fecha=fecha;
        this.costo=costo;
    }

}
