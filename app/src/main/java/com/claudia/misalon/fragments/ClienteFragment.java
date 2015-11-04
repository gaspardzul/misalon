package com.claudia.misalon.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
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

import com.claudia.misalon.BO.Cliente;
import com.claudia.misalon.BO.Servicio;
import com.claudia.misalon.R;
import com.claudia.misalon.adapters.ClienteAdapter;
import com.claudia.misalon.adapters.ServiceAdapter;
import com.claudia.misalon.listeners.RecyclerItemClickListener;
import com.claudia.misalon.views.ClienteAgregarEditar;
import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class ClienteFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    Button btnAgregarCliente;
    private RecyclerView recyclerViewCliente;
    private ClienteAdapter adapterCLiente;
    ArrayList<Cliente> clientes = new ArrayList<>();
    LinearLayoutManager mLayoutManager;
    SwipeRefreshLayout mSwipeRefreshLayout;


    public ClienteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_cliente, container, false);
        btnAgregarCliente = (Button) v.findViewById(R.id.btnAgregarCliente);
        btnAgregarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    nuevoCliente(view);
            }
        });
        mLayoutManager = new LinearLayoutManager(getActivity());
        mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_refresh_clientes);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getClienteParse();
            }
        });

        init(v);
        getClienteParse();
        return v;
    }

    public void init(View v){
        recyclerViewCliente = (RecyclerView) v.findViewById(R.id.recyclerClientes);
        adapterCLiente = new ClienteAdapter(getActivity());
        recyclerViewCliente.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), recyclerViewCliente, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                 Cliente cliente = clientes.get(position);
                 Intent detalleCliente = new Intent(getActivity(),ClienteAgregarEditar.class);
                 detalleCliente.putExtra("nombre",cliente.getNombre());
                 detalleCliente.putExtra("telefono",cliente.getTelefono());
                 detalleCliente.putExtra("fechaNacimiento",cliente.getFechaNacimiento());
                 detalleCliente.putExtra("editar",true);
                 detalleCliente.putExtra("clienteid",cliente.getClienteID());
                 startActivity(detalleCliente);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }
    ));
        setupList();
    }

    private void setupList(){
        recyclerViewCliente.setLayoutManager(mLayoutManager);
        recyclerViewCliente.setAdapter(adapterCLiente);
    }

    public void nuevoCliente(View v){
        Intent intent = new Intent(getActivity(), ClienteAgregarEditar.class);
        startActivity(intent);
    }

    //obtenemos los clientes de parse de tipo ParseObj
    public void getClienteParse(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Cliente");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, com.parse.ParseException e) {
                if (e == null) {
                    Log.d("score", "clientes " + objects.size() + " scores");
                    //obtenemos los productos a partir del resultado de la consulta
                    if (objects.size() > 0) {
                        getClientes(objects);
                    }

                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }

        });
    }

    //btenemos los clientes a partir de un listado de ParseObject
    public void getClientes(List<ParseObject> objects){
        clientes.clear();
        for(int i=0;i<objects.size();i++){
            ParseObject obj = objects.get(i);
            Cliente c = new Cliente();
            c.setNombre(obj.getString("nombre"));
            c.setTelefono(obj.getString("telefono"));
            c.setFechaNacimiento(obj.getString("fechaNacimiento"));
            c.setClienteID(obj.getObjectId());
            clientes.add(c);
        }
        adapterCLiente.replace(clientes);
        mSwipeRefreshLayout.setRefreshing(false);
    }



}
