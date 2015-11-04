package com.claudia.misalon.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.claudia.misalon.BO.Cliente;
import com.claudia.misalon.R;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class ClienteAgregarEditar extends AppCompatActivity {

    EditText txtNombre;
    EditText txtTelefono;
    EditText txtFecNac;
    boolean editar;
    String clienteIdEditar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_agregar_editar);
        txtNombre = (EditText) findViewById(R.id.txtNombre);
        txtTelefono = (EditText) findViewById(R.id.txtTelefono);
        txtFecNac = (EditText) findViewById(R.id.txtFechaNacimiento);
        editar=false;
        Intent intent = getIntent();
        if(intent.hasExtra("editar")){
            Bundle bd = getIntent().getExtras();
            txtNombre.setText(bd.getString("nombre"));
            txtTelefono.setText(bd.getString("telefono"));
            txtFecNac.setText(bd.getString("fechaNacimiento"));
            clienteIdEditar = bd.getString("clienteid");
            Log.d("id:",clienteIdEditar);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cliente_agregar_editar, menu);
        return true;
    }

    public void guardarCliente(View v){
        if(editar){
            editarCliente(clienteIdEditar);
        }else{
            Cliente c = new Cliente();
            c.setNombre(txtNombre.getText().toString());
            c.setTelefono(txtTelefono.getText().toString());
            c.setFechaNacimiento(txtFecNac.getText().toString());
            c.saveInBackground();

            Toast.makeText(this,"Cliente Guardado!",Toast.LENGTH_LONG).show();
            onBackPressed();
        }

    }


    public void editarCliente(String clienteid){
        Log.d("id",clienteid);
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Producto");

        // Retrieve the object by id
        query.getInBackground(clienteid, new GetCallback<ParseObject>() {
            public void done(ParseObject obj, ParseException e) {
                String nombre=txtNombre.getText().toString().trim();
                String telef = txtTelefono.getText().toString().trim();
                String fechaNac = txtFecNac.getText().toString().trim();
                if (e == null) {
                    // Now let's update it with some new data. In this case, only cheatMode and score
                    // will get sent to the Parse Cloud. playerName hasn't changed.
                    obj.put("nombre", nombre);
                    obj.put("telefono",telef);
                    obj.put("fechaNacimiento", fechaNac);
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

}
