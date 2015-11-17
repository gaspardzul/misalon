package com.claudia.misalon.views;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.claudia.misalon.BO.Cliente;
import com.claudia.misalon.R;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ClienteAgregarEditar extends AppCompatActivity {

    EditText txtNombre;
    EditText txtTelefono;
    EditText txtFecNac;
    boolean editar;
    String clienteIdEditar;
    Button btnEliminar;
    Calendar myCalendar = Calendar.getInstance();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_agregar_editar);
        txtNombre = (EditText) findViewById(R.id.txtNombre);
        txtTelefono = (EditText) findViewById(R.id.txtTelefono);
        txtFecNac = (EditText) findViewById(R.id.txtFechaNacimiento);
        btnEliminar = (Button) findViewById(R.id.btnEliminarCliente);
        editar=false;
        Intent intent = getIntent();
        if(intent.hasExtra("editar")){
            Bundle bd = getIntent().getExtras();
            editar = true;
            txtNombre.setText(bd.getString("nombre"));
            txtTelefono.setText(bd.getString("telefono"));
            btnEliminar.setVisibility(View.VISIBLE);
            txtFecNac.setText(bd.getString("fechaNacimiento"));
            clienteIdEditar = bd.getString("clienteid");
            Log.d("id:",clienteIdEditar);
        }else{
            btnEliminar.setVisibility(View.GONE);
        }
        txtFecNac.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focus) {
                // TODO Auto-generated method stub
                if(focus){
                    new DatePickerDialog(ClienteAgregarEditar.this, date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cliente_agregar_editar, menu);
        return true;
    }


    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };

    private void updateLabel() {

        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        txtFecNac.setText(sdf.format(myCalendar.getTime()));
    }


    /**
     * Crea un diálogo de alerta sencillo
     * @return Nuevo diálogo
     */
    public AlertDialog alertEliminarCliente() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Alerta")
                .setMessage("¿Realmente desea eliminar el registro?")
                .setPositiveButton("ELIMINAR",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d("id",clienteIdEditar);
                                ParseQuery<ParseObject> query = ParseQuery.getQuery("Cliente");
                                // Retrieve the object by id
                                query.getInBackground(clienteIdEditar, new GetCallback<ParseObject>() {
                                    public void done(ParseObject obj, ParseException e) {
                                        if (e == null) {
                                            // Now let's update it with some new data. In this case, only cheatMode and score
                                            // will get sent to the Parse Cloud. playerName hasn't changed.

                                            obj.deleteInBackground();
                                            Log.d("note", "Eliminando...");
                                            Toast.makeText(getApplication(),"Eliminado correctamente",Toast.LENGTH_SHORT).show();
                                            onBackPressed();
                                        }else {
                                            Log.d("msj",e.getMessage());
                                        }
                                    }
                                });
                            }
                        })
                .setNegativeButton("CANCELAR",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

        return builder.create();
    }

    public void eliminarCliente(View v){
        AlertDialog dialog = alertEliminarCliente();
        dialog.show();
    }


    public void guardarCliente(View v){
        if(editar){
            editarCliente(clienteIdEditar);
        }else{
            Cliente c = new Cliente();
            c.setNombre(txtNombre.getText().toString().toUpperCase());
            c.setTelefono(txtTelefono.getText().toString());
            c.setFechaNacimiento(txtFecNac.getText().toString());
            c.saveInBackground();

            Toast.makeText(this,"Cliente Guardado!",Toast.LENGTH_LONG).show();
            onBackPressed();
        }

    }


    public void editarCliente(String clienteid){
        Log.d("id",clienteid);
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Cliente");

        // Retrieve the object by id
        query.getInBackground(clienteid, new GetCallback<ParseObject>() {
            public void done(ParseObject obj, ParseException e) {
                String nombre=txtNombre.getText().toString().trim().toUpperCase();
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
