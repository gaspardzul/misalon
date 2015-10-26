package com.claudia.misalon.BO;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by gaspar on 26/07/15.
 */
@ParseClassName("Producto")
public class Producto extends ParseObject{

    private String nombre;
    private double precio;
    private int tipo; // es producto o servicio
    private String idProd;

    public String getNombre() {
        return getString("nombre");
    }

    public void setNombre(String nombre) {
        put("nombre",nombre);
    }

    public double getPrecio() {
        return getDouble("precio");
    }

    public void setPrecio(double precio) {
        put("precio",precio);
    }

    public int getTipo() {
        return getInt("tipo");
    }

    public void setTipo(int tipo) {
        put("tipo",tipo);
    }

    public String getId(){
        return getObjectId();
    }

    public String getIdProd() {
        return idProd;
    }

    public void setIdProd(String idProd) {
        this.idProd = idProd;
    }
}
