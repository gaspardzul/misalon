package com.claudia.misalon.BO;
import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by gaspar on 26/07/15.
 */
@ParseClassName("Cliente")
public class Cliente extends ParseObject {

    private String nombre;
    private String telefono;
    private String fechaNacimiento;
    private int visitas;


    public String getNombre() {
        return getString("nombre");
    }

    public void setNombre(String nombre) {
        put("nombre",nombre);
    }

    public String getTelefono() {
        return getString("telefono");
    }

    public void setTelefono(String telefono) {
        put("telefono",telefono);
    }

    public String getFechaNacimiento() {
        return getString("fechaNacimiento");
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        put("fechaNacimiento",fechaNacimiento);
    }

    public int getVisitas() {
        return getInt("visitas");
    }

    public void setVisitas(int visitas) {
        put("visitas",visitas);
    }
}
