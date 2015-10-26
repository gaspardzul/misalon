package com.claudia.misalon.fragments;


import android.app.ProgressDialog;
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
import com.claudia.misalon.R;
import com.claudia.misalon.adapters.ProductAdapter;
import com.claudia.misalon.listeners.RecyclerItemClickListener;
import com.claudia.misalon.views.ProductoAgregarEditar;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductoServiciosFragment extends Fragment {

    private RecyclerView recyclerViewProduct;
    private ProductAdapter adapterProduct;
    ArrayList<Producto> productos =new  ArrayList<>();
    LinearLayoutManager mLayoutManager;
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

        Button btnAgregar = (Button) v.findViewById(R.id.btnAgregarProducto);
        mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_refresh_products);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getProductosParse();
            }
        });
        mLayoutManager = new LinearLayoutManager(getActivity());

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agregarProduc();
            }
        });
        init(v);
        getProductosParse();
        return v;
    }

    private void init(View v) {
        recyclerViewProduct = (RecyclerView) v.findViewById(R.id.recyclerProducts);
        recyclerViewProduct.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), recyclerViewProduct, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //al hacer clic en algun producto
                Producto producto = productos.get(position);

                Intent detalleProducto = new Intent(getActivity(), ProductoAgregarEditar.class);
                detalleProducto.putExtra("productoId", producto.getIdProd());
                detalleProducto.putExtra("productoNombre", producto.getNombre());
                detalleProducto.putExtra("productoPrecio", producto.getPrecio());
                detalleProducto.putExtra("productoTipo", producto.getTipo());
                detalleProducto.putExtra("editar", true);
                startActivity(detalleProducto);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));
        adapterProduct = new ProductAdapter(getActivity());
        setupList();
    }

    private void setupList(){
        recyclerViewProduct.setLayoutManager(mLayoutManager);
        recyclerViewProduct.setAdapter(adapterProduct);
    }


    //obtenemos los productos de parse de tipo ParseObject
    public void getProductosParse(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Producto");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, com.parse.ParseException e) {
                if (e == null) {
                    Log.d("score", "Retrieved " + objects.size() + " scores");
                    //obtenemos los productos a partir del resultado de la consulta
                    if(objects.size()>0){
                        getProducts(objects);
                    }

                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }

        });

    }

    //obtenemos los productos a partir de un listado de ParseObject
    public void getProducts(List<ParseObject> objects){
        productos.clear();
        for(int i=0;i<objects.size();i++){
            ParseObject obj = objects.get(i);
            Producto p = new Producto();
            p.setNombre(obj.getString("nombre"));
            p.setTipo(obj.getInt("tipo"));
            p.setPrecio(obj.getDouble("precio"));
            p.setIdProd(obj.getObjectId());
            productos.add(p);
        }
        adapterProduct.replace(productos);
        mSwipeRefreshLayout.setRefreshing(false);
    }



    public void agregarProduc(){
        Intent intent = new Intent(getActivity(),ProductoAgregarEditar.class);
        startActivity(intent);
    }


}
