package com.claudia.misalon.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.claudia.misalon.BO.Cliente;
import com.claudia.misalon.R;

public class ClienteAgregarEditar extends AppCompatActivity {

    EditText txtNombre;
    EditText txtTelefono;
    EditText txtFecNac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_agregar_editar);
        txtNombre = (EditText) findViewById(R.id.txtNombre);
        txtTelefono = (EditText) findViewById(R.id.txtTelefono);
        txtFecNac = (EditText) findViewById(R.id.txtFechaNacimiento);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cliente_agregar_editar, menu);
        return true;
    }

    public void guardarCliente(View v){
        Cliente c = new Cliente();
        c.setNombre(txtNombre.getText().toString());
        c.setTelefono(txtTelefono.getText().toString());
        c.setFechaNacimiento(txtFecNac.getText().toString());
        c.saveInBackground();

        Toast.makeText(this,"Cliente Guardado!",Toast.LENGTH_LONG).show();
        onBackPressed();
    }

}
