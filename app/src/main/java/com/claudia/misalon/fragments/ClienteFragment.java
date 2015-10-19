package com.claudia.misalon.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.claudia.misalon.R;
import com.claudia.misalon.views.ClienteAgregarEditar;

public class ClienteFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    Button btnAgregarCliente;


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
        return v;
    }

    public void nuevoCliente(View v){
        Intent intent = new Intent(getActivity(), ClienteAgregarEditar.class);
        startActivity(intent);
    }



}
