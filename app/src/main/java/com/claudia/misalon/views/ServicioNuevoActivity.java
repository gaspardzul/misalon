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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.claudia.misalon.BO.Cliente;
import com.claudia.misalon.BO.Producto;
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

public class ServicioNuevoActivity extends AppCompatActivity  implements AdapterView.OnItemSelectedListener{

    Button btnGuardarServicio;
    TextView txtTotal;
    public static String clientID;
    public static  ArrayList<String> arrServicios;
    public static double costoActual;
    public final String textTotal = "Total: $ ";
    public ArrayList<String> serviciosList = new ArrayList<>();
    public ArrayList<String> clientesList = new ArrayList<>();
    String nombreProducto="";
    String nombreCliente="";
    Button btnEliminarProducto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicio_nuevo);
        btnGuardarServicio = (Button) findViewById(R.id.btnGuardarServicio);
        arrServicios = new ArrayList<String>();
        txtTotal = (TextView) findViewById(R.id.txtTotal);
        btnEliminarProducto=(Button) findViewById(R.id.btnRmService);
        btnEliminarProducto.setVisibility(View.GONE);
        costoActual=0;
        getProductServices();
        getClients();
        setToolbar();
    }


    public void setSpinnerServicios(){
        Spinner spinner = (Spinner) findViewById(R.id.spinnerService);
        // Application of the Array to the Spinner
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, serviciosList);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1); // The drop down view
        spinner.setAdapter(spinnerArrayAdapter);
        spinner.setOnItemSelectedListener(this);
    }



    public void setSpinnerClientes(){
        Spinner spinner = (Spinner) findViewById(R.id.spinnerCliente);
        // Application of the Array to the Spinner
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, clientesList);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1); // The drop down view
        spinner.setAdapter(spinnerArrayAdapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_servicio_nuevo, menu);
        return true;
    }

    public void getProductServices(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Producto");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    //existen elementos
                    for (int i = 0; i < objects.size(); i++) {
                        serviciosList.add(objects.get(i).getString("nombre"));
                    }
                    //asignamos elarreglo al spinner (al dropdown list)
                    setSpinnerServicios();
                    nombreProducto = serviciosList.get(0);
                } else {
                }
            }
        });
    }

    public void getClients(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Cliente");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    //existen elementos
                    for (int i = 0; i < objects.size(); i++) {
                        clientesList.add(objects.get(i).getString("nombre"));
                    }
                    //asignamos elarreglo al spinner (al dropdown list)
                    setSpinnerClientes();
                } else {
                }
            }
        });
    }


    public void addProduct(View v){

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Producto");
        query.whereEqualTo("nombre", nombreProducto);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    // object will be your game score
                    arrServicios.add(object.getString("nombre"));
                    costoActual += object.getDouble("precio");
                    Log.d("costo", String.valueOf(costoActual));
                    txtTotal.setText(textTotal + costoActual);
                    btnEliminarProducto.setVisibility(View.VISIBLE);
                } else {
                    // something went wrong
                    Toast.makeText(getApplication(), "Producto no encontrado", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //quita producto del servicio
    public void rmProduct(View v){
        Log.d("entra", "entra");
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Producto");
        query.whereEqualTo("nombre", nombreProducto);
        btnEliminarProducto.setVisibility(View.GONE);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    String nombreP = object.getString("nombre");
                    //nos sirve para eliminar de la lista de arreglos de producto el producto que estamos eliminando
                    for(int i=0;i<arrServicios.size();i++){
                        if (arrServicios.get(i).equals(nombreP)){
                            arrServicios.remove(i);
                            break;
                        }
                    }
                    //ciclo para ver si existe otro producto igual en el arreglo de productos
                    for(int i=0;i<arrServicios.size();i++){
                        if (arrServicios.get(i).equals(nombreP)){
                            btnEliminarProducto.setVisibility(View.VISIBLE);
                            break;
                        }
                    }



                    costoActual -= object.getDouble("precio");
                    Log.d("costo", String.valueOf(costoActual));
                    txtTotal.setText(textTotal + costoActual);
                    Toast.makeText(getApplication(), "Eliminado "+nombreP, Toast.LENGTH_SHORT).show();
                    if(arrServicios.size()==0){
                        btnEliminarProducto.setVisibility(View.GONE);
                    }
                } else {
                    // something went wrong
                    Toast.makeText(getApplication(), "Producto no encontrado", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void saveServicio(View v) {
        ///validamos que el costo no sea 0
        if (costoActual > 0 && !nombreCliente.equals("")) {
            //hay productos agregados
            ///validamos cliente
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Cliente");
            query.whereEqualTo("nombre", nombreCliente);
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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        int type_view = adapterView.getId();
        btnEliminarProducto.setVisibility(View.GONE);
        if(type_view==R.id.spinnerService){
            nombreProducto = serviciosList.get(position);
            for(int i=0;i<arrServicios.size();i++){
                if (arrServicios.get(i).equals(nombreProducto)){
                    btnEliminarProducto.setVisibility(View.VISIBLE);
                    break;
                }
            }
        }else if(type_view==R.id.spinnerCliente){
            nombreCliente = clientesList.get(position);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
