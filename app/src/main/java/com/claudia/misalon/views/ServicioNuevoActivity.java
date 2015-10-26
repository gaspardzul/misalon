package com.claudia.misalon.views;

import android.support.design.widget.NavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.claudia.misalon.BO.Cliente;
import com.claudia.misalon.BO.Servicio;
import com.claudia.misalon.R;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ServicioNuevoActivity extends AppCompatActivity {

    Button btnGuardarServicio;
    EditText txtNombre;
    EditText txtServicios;
    TextView txtTotal;
    public static String clientID;
    public static  ArrayList<String> arrServicios;
    public static double costoActual;
    public final String textTotal = "Total: $ ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicio_nuevo);
        btnGuardarServicio = (Button) findViewById(R.id.btnGuardarServicio);
        txtNombre = (EditText) findViewById(R.id.txtClienteServicio);
        txtServicios = (EditText) findViewById(R.id.txtServicio);
        arrServicios = new ArrayList<String>();
        txtTotal = (TextView) findViewById(R.id.txtTotal);
        costoActual=0;
        setToolbar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_servicio_nuevo, menu);
        return true;
    }


    public void addProduct(View v){
        String strservicio = txtServicios.getText().toString();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Producto");
        query.whereEqualTo("nombre", strservicio);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    // object will be your game score
                    arrServicios.add(object.getString("nombre"));
                    costoActual += object.getDouble("precio");
                    Log.d("costo", String.valueOf(costoActual));
                    txtTotal.setText(textTotal + costoActual);
                    txtServicios.setText("");
                } else {
                    // something went wrong
                    Toast.makeText(getApplication(), "Producto no encontrado", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //quita producto del servicio
    public void rmProduct(View v){
        Log.d("entra","entra");
        String strservicio = txtServicios.getText().toString();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Producto");
        query.whereEqualTo("nombre", strservicio);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    // object will be your game score
                    arrServicios.add(object.getObjectId());
                    costoActual -= object.getDouble("precio");
                    Log.d("costo", String.valueOf(costoActual));
                    txtTotal.setText(textTotal + costoActual);
                    txtServicios.setText("");
                } else {
                    // something went wrong
                    Toast.makeText(getApplication(), "Producto no encontrado", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void saveServicio(View v) {
        ///validamos que el costo no sea 0
        if (costoActual > 0 && !txtNombre.getText().equals("")) {
            //hay productos agregados
            ///validamos cliente
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Cliente");
            query.whereEqualTo("nombre", txtNombre.getText().toString().trim());
            query.getFirstInBackground(new GetCallback<ParseObject>() {
                public void done(ParseObject object, ParseException e) {
                    if (object == null) {
                        Toast.makeText(getApplication(), "Cliente no encontrado", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.d("score", "Retrieved the object.");
                        String nombre = object.getString("nombre");
                        clientID = object.getObjectId();
                        Servicio s = new Servicio();
                        s.setClienteID(clientID);
                        s.setCliente(nombre);
                        s.setServicios(arrServicios);
                        s.setFecha(new Date());
                        s.setCosto(costoActual);
                        s.saveInBackground();
                        Log.d("pass", "servicio guardado!");
                        costoActual=0;
                        txtTotal.setText(textTotal + costoActual);
                        Toast.makeText(getApplication(), "Servicio Guardado!", Toast.LENGTH_SHORT).show();
                        onBackPressed();

                    }
                }
            });
        } else {
            Toast.makeText(getApplication(), "Ingresar Datos", Toast.LENGTH_SHORT).show();
        }


    }


    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            // Poner ícono del drawer toggle
            ab.setHomeAsUpIndicator(R.drawable.ic_menu);
            ab.setDisplayHomeAsUpEnabled(true);
        }
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
