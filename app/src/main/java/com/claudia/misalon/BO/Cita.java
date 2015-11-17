package com.claudia.misalon.BO;



import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by gaspar on 26/07/15.
 */
@ParseClassName("Cita")
public class Cita extends ParseObject{
    private String nombre; //cliente
    private Date fecha; //fecha
    private String descripcion;
    boolean activo;
    private String citaID;

    public String getNombre() {
        return getString("nombre");
    }

    public void setNombre(String nombre) {
        put("nombre",nombre);
    }

    public Date getFecha() {
        return getDate("fecha");
    }

    public void setFecha(Date fecha) {
        put("fecha",fecha);
    }

    public String getDescripcion() {
        return getString("descripcion");
    }

    public void setDescripcion(String descripcion) {
        put("descripcion",descripcion);
    }

    public String getCitaID() {
        return citaID;
    }

    public void setCitaID(String citaID) {
        this.citaID = citaID;
    }
}
