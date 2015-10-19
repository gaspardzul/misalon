package com.claudia.misalon.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.claudia.misalon.BO.Producto;
import com.claudia.misalon.R;
import com.claudia.misalon.adapters.ProductAdapter;
import com.claudia.misalon.views.ProductoAgregarEditar;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductoServiciosFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager linearLayoutManager;
    SwipeRefreshLayout mSwipeRefreshLayout;
    View vMain;

    public ProductoServiciosFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflatex the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_producto_servicios, container, false);
        vMain = v;

        Button btnAgregar = (Button) v.findViewById(R.id.btnAgregarProducto);
        final SwipeRefreshLayout mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_refresh_products);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                init(vMain,mSwipeRefreshLayout);
            }
        });

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agregarProduc();
            }
        });

        init(v,mSwipeRefreshLayout);
        return v;

    }

    private void init(View v, SwipeRefreshLayout s) {

        //List<Producto> productos = Producto.listAll(Producto.class);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerProducts);
        //adapter = new ProductAdapter(getActivity(),productos);
        recyclerView.setLayoutManager(new LinearLayoutManager((getActivity())));
        recyclerView.setAdapter(adapter);
        s.setRefreshing(false);
    }


    public void agregarProduc(){
        Intent intent = new Intent(getActivity(),ProductoAgregarEditar.class);
        startActivity(intent);
    }


}
