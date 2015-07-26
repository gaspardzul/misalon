package com.claudia.misalon.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.claudia.misalon.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductoServiciosFragment extends Fragment {


    public ProductoServiciosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_producto_servicios, container, false);
    }


}
