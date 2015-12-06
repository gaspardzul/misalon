package com.claudia.misalon.BO;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by gaspar on 26/07/15.
 * son los servicios que se hacen los clientes
 */
@ParseClassName("Servicio")
public class Servicio extends ParseObject{

    private String cliente;
    private String servicioID;
    private String clienteID;
    private ArrayList<String> servicios; //ids de servicios
    private Date fecha;
    private double costo;


    public String getCliente() {
        return getString("cliente");
    }

    public void setCliente(String cliente) {
        put("cliente",cliente);
    }

    public ArrayList<String> getServicios() {
        return (ArrayList<String>) get("servicios");
    }

    public void setServicios(ArrayList<String> servicios) {
        put("servicios", servicios);
    }

    public Date getFecha() {
        return getDate("fecha");
    }

    public void setFecha(Date fecha) {
        put("fecha",fecha);
    }

    public double getCosto() {
        return getDouble("costo");
    }

    public void setCosto(double costo) {
        put("costo",costo);
    }

    public String getClienteID() {
        return getString("clienteid");
    }

    public void setClienteID(String clienteID) {
       put("clienteid",clienteID);
    }

    public String getServicioID() {
        return servicioID;
    }

    public void setServicioID(String servicioID) {
        this.servicioID = servicioID;
    }
}
