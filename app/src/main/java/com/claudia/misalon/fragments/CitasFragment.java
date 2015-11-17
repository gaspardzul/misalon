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

import com.claudia.misalon.BO.Cita;
import com.claudia.misalon.BO.Cliente;
import com.claudia.misalon.R;
import com.claudia.misalon.adapters.CitaAdapter;
import com.claudia.misalon.listeners.RecyclerItemClickListener;
import com.claudia.misalon.views.ClienteAgregarEditar;
import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CitasFragment extends Fragment {

    private RecyclerView recyclerViewCita;
    private CitaAdapter adapterCita;
    ArrayList<Cita> citas = new ArrayList<>();
    LinearLayoutManager mLayoutManager;
    SwipeRefreshLayout mSwipeRefreshLayout;

    public CitasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_citas, container, false);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_refresh_citas);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getCitasParse();
            }
        });

        init(v);
        getCitasParse();
        return v;
    }

    public void init(View v){
        recyclerViewCita = (RecyclerView) v.findViewById(R.id.recyclerCitas);
        adapterCita = new CitaAdapter(getActivity());
        recyclerViewCita.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), recyclerViewCita, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //ver detalle
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));
        setupList();

    }

    private void setupList(){
        recyclerViewCita.setLayoutManager(mLayoutManager);
        recyclerViewCita.setAdapter(adapterCita);
    }


    public void  getCitasParse(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Cita");
        query.orderByAscending("fecha");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, com.parse.ParseException e) {
                if (e == null) {
                    Log.d("score", "citas " + objects.size() + " scores");
                    //obtenemos los productos a partir del resultado de la consulta
                    if (objects.size() > 0) {
                        getCitas(objects);
                    }

                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }

        });
    }

    public void getCitas(List<ParseObject> objects){
        citas.clear();
        for(int i=0;i<objects.size();i++){
            ParseObject obj = objects.get(i);
            Cita c = new Cita();
            c.setNombre(obj.getString("nombre"));
            c.setFecha(obj.getDate("fecha"));
            c.setDescripcion(obj.getString("descripcion"));
            c.setCitaID(obj.getObjectId());
            citas.add(c);
        }
        adapterCita.replace(citas);
        mSwipeRefreshLayout.setRefreshing(false);
    }

}
