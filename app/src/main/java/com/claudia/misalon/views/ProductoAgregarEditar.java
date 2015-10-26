package com.claudia.misalon.views;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import com.claudia.misalon.BO.Producto;
import com.claudia.misalon.R;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class ProductoAgregarEditar extends ActionBarActivity {

    int tipoProducto;
    EditText txtNombre;
    EditText txtPrecio;
    String productIdEditar;
    RadioButton radioServicio;
    RadioButton radioProducto;

    boolean editar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto_agregar_editar);
        setToolbar();
        txtNombre = (EditText) findViewById(R.id.txtNombreProducto);
        txtPrecio = (EditText) findViewById(R.id.txtPrecioProducto);
        radioServicio = (RadioButton)findViewById(R.id.radio_servicio);
        radioProducto = (RadioButton)findViewById(R.id.radio_producto);
        editar=false;
        Intent intent = getIntent();
        if(intent.hasExtra("editar")){
            Bundle bd = getIntent().getExtras();
            productIdEditar = bd.getString("productoId");
            txtNombre.setText(bd.getString("productoNombre"));
            txtPrecio.setText(""+bd.getDouble("productoPrecio"));
            int tipo = bd.getInt("productoTipo");
            tipoProducto=tipo;
            if(tipo==1){
                //producto
                radioServicio.setChecked(true);
            }else{
                //servicio
                radioProducto.setChecked(true);
            }
            editar = true;
        }

    }

    public void cancelar(View v){
        onBackPressed();
    }

    public void editarProdut(String idProd){
        Log.d("id",idProd);
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Producto");

        // Retrieve the object by id
        query.getInBackground(idProd, new GetCallback<ParseObject>() {
            public void done(ParseObject obj, ParseException e) {
                String nombre=txtNombre.getText().toString().toLowerCase().trim();
                double precio = Double.parseDouble(txtPrecio.getText().toString().trim());
                if (e == null) {
                    // Now let's update it with some new data. In this case, only cheatMode and score
                    // will get sent to the Parse Cloud. playerName hasn't changed.
                    obj.put("nombre", nombre);
                    obj.put("precio", precio);
                    obj.put("tipo", tipoProducto);
                    obj.saveInBackground();
                    Log.d("note", "guardando");
                    Toast.makeText(getApplication(),"Editado correctamente",Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }else {
                    Log.d("msj",e.getMessage());
                }
            }
        });
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

        if(editar){
            editarProdut(productIdEditar);
        }else{
            String nombre=txtNombre.getText().toString().toLowerCase().trim();
            double precio = Double.parseDouble(txtPrecio.getText().toString().trim());
            Producto p = new Producto();
            try{
                p.setNombre(nombre);
                p.setPrecio(precio);
                p.setTipo(tipoProducto);
                p.saveInBackground();
                Toast.makeText(this,"Producto/Servicio guardado!",Toast.LENGTH_SHORT).show();
                onBackPressed();
            }catch(Exception e){
                Log.e("error::::", e.getMessage());
            }
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
