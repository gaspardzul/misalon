package com.claudia.misalon.BO;

import com.orm.SugarRecord;

/**
 * Created by gaspar on 26/07/15.
 */

public class Cliente extends SugarRecord<Cliente> {

    String nombre;
    String telefono;
    int sellos;
    int visitas;

    public  Cliente(){}

    public Cliente(String nombre, String telefono, int sellos,int visitas){
        this.nombre = nombre;
        this.telefono = telefono;
        this.sellos = sellos;
        this.visitas = visitas;
    }

}
