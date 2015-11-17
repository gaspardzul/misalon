package com.claudia.misalon.views;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.claudia.misalon.BO.Cita;
import com.claudia.misalon.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CitaActivity extends AppCompatActivity  implements AdapterView.OnItemSelectedListener{

    public ArrayList<String> clientesList = new ArrayList<>();
    String nombreCliente="";
    EditText txtFecCita;
    EditText txtHoraCita;
    EditText txtDescripcion;

    Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cita);
        getClients();
        txtFecCita = (EditText) findViewById(R.id.txtFechaCita);
        txtHoraCita = (EditText) findViewById(R.id.txtHoraCita);
        txtDescripcion = (EditText) findViewById(R.id.txtCitaDescripcion);
        txtHoraCita.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focus) {
                if(focus){
                    Calendar mcurrentTime = Calendar.getInstance();
                    int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                    int minute = mcurrentTime.get(Calendar.MINUTE);
                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(CitaActivity.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                            String am_pm = "";

                            Calendar datetime = Calendar.getInstance();
                            datetime.set(Calendar.HOUR_OF_DAY, selectedHour);
                            datetime.set(Calendar.MINUTE, selectedMinute);

                            if (datetime.get(Calendar.AM_PM) == Calendar.AM)
                                am_pm = "AM";
                            else if (datetime.get(Calendar.AM_PM) == Calendar.PM)
                                am_pm = "PM";

                            String strHrsToShow = (datetime.get(Calendar.HOUR) == 0) ?"12":Integer.toString( datetime.get(Calendar.HOUR) );
                            txtHoraCita.setText(strHrsToShow+":"+datetime.get(Calendar.MINUTE)+" "+am_pm);
                        }
                    }, hour, minute, false);//Yes 24 hour time
                    mTimePicker.setTitle("Hora de la cita");
                    mTimePicker.show();
                }

            }
        });

        txtHoraCita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(CitaActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String am_pm = "";

                        Calendar datetime = Calendar.getInstance();
                        datetime.set(Calendar.HOUR_OF_DAY, selectedHour);
                        datetime.set(Calendar.MINUTE, selectedMinute);

                        if (datetime.get(Calendar.AM_PM) == Calendar.AM)
                            am_pm = "AM";
                        else if (datetime.get(Calendar.AM_PM) == Calendar.PM)
                            am_pm = "PM";

                        String strHrsToShow = (datetime.get(Calendar.HOUR) == 0) ? "12" : Integer.toString(datetime.get(Calendar.HOUR));
                        txtHoraCita.setText(strHrsToShow + ":" + datetime.get(Calendar.MINUTE) + " " + am_pm);
                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle("Hora de la cita");
                mTimePicker.show();
            }
        });

        txtFecCita.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focus) {
                // TODO Auto-generated method stub
                if (focus) {
                    DatePickerDialog mDatePicker;
                    mDatePicker = new DatePickerDialog(CitaActivity.this, date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH));
                    mDatePicker.setTitle("Fecha de cita");
                    mDatePicker.show();
                }

            }
        });


        txtFecCita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(CitaActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }


    public void guardarCita(View v){
        try{
            Cita cita = new Cita();
            cita.setNombre(nombreCliente);
            String strFecha = txtFecCita.getText().toString().trim();
            String strHora = txtHoraCita.getText().toString().trim();
            Date fechaDate = new Date(strFecha +" "+ strHora);
            Date fechaNow = new Date();
            cita.setFecha(fechaDate);
            cita.setDescripcion(txtDescripcion.getText().toString().trim());
            cita.saveInBackground();
            Toast.makeText(this,"Cita guardada",Toast.LENGTH_LONG).show();
            onBackPressed();
        }catch (Exception e){
            Log.d("eerror", e.getMessage());
        }

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
        txtFecCita.setText(sdf.format(myCalendar.getTime()));
    }


    public void setSpinnerClientes(){
        Spinner spinner = (Spinner) findViewById(R.id.spinnerCliente);
        // Application of the Array to the Spinner
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, clientesList);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1); // The drop down view
        spinner.setAdapter(spinnerArrayAdapter);
        spinner.setOnItemSelectedListener(this);
    }

    public void getClients(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Cliente");
        query.orderByAscending("nombre");
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cita, menu);
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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        nombreCliente = clientesList.get(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
