package com.claudia.misalon.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.claudia.misalon.R;
import com.claudia.misalon.views.ClienteAgregarEditar;
import com.claudia.misalon.views.ServicioNuevoActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ServicioFragment extends Fragment {

    Button btnNuevoServicio;

    public ServicioFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_servicio, container, false);
        btnNuevoServicio= (Button) v.findViewById(R.id.btnNuevoServicio);
        btnNuevoServicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nuevoServicio(view);
            }
        });
        return v;
    }


    public void nuevoServicio(View v) {
        Intent intent = new Intent(getActivity(), ServicioNuevoActivity.class);
        startActivity(intent);
    }


}
