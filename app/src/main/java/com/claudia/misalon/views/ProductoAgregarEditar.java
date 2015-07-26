package com.claudia.misalon.views;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import com.claudia.misalon.BO.Producto;
import com.claudia.misalon.R;

public class ProductoAgregarEditar extends ActionBarActivity {

    int tipoProducto;
    EditText txtNombre;
    EditText txtPrecio;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto_agregar_editar);
        setToolbar();
        txtNombre = (EditText) findViewById(R.id.txtNombreProducto);
        txtPrecio = (EditText) findViewById(R.id.txtPrecioProducto);
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            // Poner ícono del drawer toggle
            ab.setTitle("Agregar/Editar Productos");
        }
    }

    public void guardarProducto(View view){
        String nombre=txtNombre.getText().toString().toLowerCase().trim();
        double precio = Double.parseDouble(txtPrecio.getText().toString().trim());
        Producto p = new Producto(nombre,precio,tipoProducto);
        try{
            p.save();
            onBackPressed();
        }catch(Exception e){
            Log.e("error::::",e.getMessage());
        }

    }


    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_servicio:
                if (checked)
                    tipoProducto=0;
                    // Pirates are the best
                    break;
            case R.id.radio_producto:
                if (checked)
                    // Ninjas rule
                    tipoProducto=1;
                    break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_producto_agregar_editar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
