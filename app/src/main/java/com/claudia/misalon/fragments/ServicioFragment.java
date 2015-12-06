package com.claudia.misalon.fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.claudia.misalon.BO.Producto;
import com.claudia.misalon.BO.Servicio;
import com.claudia.misalon.R;
import com.claudia.misalon.adapters.ServiceAdapter;
import com.claudia.misalon.listeners.RecyclerItemClickListener;
import com.claudia.misalon.views.ClienteAgregarEditar;
import com.claudia.misalon.views.ServicioNuevoActivity;
import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ServicioFragment extends Fragment {


    private RecyclerView recyclerViewService;
    private ServiceAdapter adapterService;
    ArrayList<Servicio> servicios = new ArrayList<>();
    LinearLayoutManager mLayoutManager;
    SwipeRefreshLayout mSwipeRefreshLayout;
    Servicio servicioActual;


    public ServicioFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_servicio, container, false);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_refresh_services);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getServicesParse();
            }
        });

        init(v);
        getServicesParse();
        return v;
    }


    public void init(View v){
        recyclerViewService = (RecyclerView) v.findViewById(R.id.recyclerServices);
        recyclerViewService.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), recyclerViewService, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                AlertDialog dialog = alertDetalleServicio(servicios.get(position));
                dialog.show();

            }

            @Override
            public void onItemLongClick(View view, int position) {
                Log.d("longClikc","click");
                servicioActual = servicios.get(position);
                AlertDialog dialog = alertEliminarServicio(servicioActual);
                dialog.show();
            }
        }));
        adapterService = new ServiceAdapter(getActivity());
        setupList();
    }

    public AlertDialog alertDetalleServicio(Servicio s){
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Detalle de servicio");
        String strMessage = "";
        SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yy hh:mm a");
        String currentDateTimeString = sdf.format(s.getFecha());
        strMessage+="Cliente: "+s.getCliente()+"\n";
        strMessage+="Fecha: "+currentDateTimeString+"\n";
        strMessage+="Monto: $"+s.getCosto()+"\n";
        strMessage+="Servicios:\n";
        ArrayList<String> arrayList = s.getServicios();
        for(String serv:arrayList){
            strMessage += "- "+serv+"\n";
        }
        builder.setMessage(strMessage);
        return builder.create();
    }

    private void setupList(){
        recyclerViewService.setLayoutManager(mLayoutManager);
        recyclerViewService.setAdapter(adapterService);
    }

    public void nuevoServicio(View v) {
        Intent intent = new Intent(getActivity(), ServicioNuevoActivity.class);
        startActivity(intent);
    }


    //obtenemos los servicios de parse de tipo ParseObject
    public void getServicesParse(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Servicio");
        query.orderByDescending("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, com.parse.ParseException e) {
                if (e == null) {
                    Log.d("score", "Retrieved " + objects.size() + " scores");
                    //obtenemos los productos a partir del resultado de la consulta
                    if (objects.size() > 0) {
                        getServices(objects);
                    } else {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                } else {
                    mSwipeRefreshLayout.setRefreshing(false);
                    Log.d("score", "Error: " + e.getMessage());
                }
            }

        });
    }


    //btenemos los servicios a partir de un listado de ParseObject
    public void getServices(List<ParseObject> objects){
        servicios.clear();
        for(int i=0;i<objects.size();i++){
            ParseObject obj = objects.get(i);
            Servicio s = new Servicio();
            ArrayList<String> list = (ArrayList<String>) obj.get("servicios");
            Log.d("cliente", obj.getString("cliente"));
            s.setServicioID(obj.getObjectId());
            s.setCliente(obj.getString("cliente"));
            s.setClienteID(obj.getString("clienteid"));
            s.setCosto(obj.getDouble("costo"));
            s.setFecha(obj.getDate("fecha"));
            s.setServicios(list);
            servicios.add(s);
        }
        adapterService.replace(servicios);
        mSwipeRefreshLayout.setRefreshing(false);
    }


    public void eliminarServicio(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Servicio");
        // Retrieve the object by id
        query.getInBackground(servicioActual.getServicioID(), new GetCallback<ParseObject>() {
            public void done(ParseObject obj, ParseException e) {
                if (e == null) {
                    // Now let's update it with some new data. In this case, only cheatMode and score
                    // will get sent to the Parse Cloud. playerName hasn't changed.
                    obj.deleteInBackground(new DeleteCallback() {
                        @Override
                        public void done(ParseException e) {
                            Toast.makeText(getActivity(), "Eliminado correctamente", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Log.d("msj", e.getMessage());
                }
            }
        });
    }

    public AlertDialog alertEliminarServicio(Servicio s) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Alerta")
                .setMessage("¿Realmente desea eliminar el servicio de "+s.getCliente()+"?")
                .setPositiveButton("ELIMINAR",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                eliminarServicio();
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

}
