package com.claudia.misalon.fragments;


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

import com.claudia.misalon.BO.Producto;
import com.claudia.misalon.BO.Servicio;
import com.claudia.misalon.R;
import com.claudia.misalon.adapters.ServiceAdapter;
import com.claudia.misalon.views.ClienteAgregarEditar;
import com.claudia.misalon.views.ServicioNuevoActivity;
import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;

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
        adapterService = new ServiceAdapter(getActivity());
        setupList();
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
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, com.parse.ParseException e) {
                if (e == null) {
                    Log.d("score", "Retrieved " + objects.size() + " scores");
                    //obtenemos los productos a partir del resultado de la consulta
                    if (objects.size() > 0) {
                        getServices(objects);
                    }else{
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
            Log.d("cliente",obj.getString("cliente"));
            s.setCliente(obj.getString("cliente"));
            s.setClienteID(obj.getString("clienteid"));
            s.setCosto(obj.getDouble("costo"));
            s.setFecha(obj.getDate("fecha"));
            servicios.add(s);
        }
        adapterService.replace(servicios);
        mSwipeRefreshLayout.setRefreshing(false);
    }

}
