package com.claudia.misalon.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.claudia.misalon.BO.Servicio;
import com.claudia.misalon.R;
import com.claudia.misalon.utils.DateUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by gaspar on 31/10/15.
 */
public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder> {

    public List<Servicio> servicios;
    static Context context;
    DateUtils dateUtils;

    public ServiceAdapter(Context ctx){
        this.context= ctx;
        this.servicios = new ArrayList<>();
    }

    public void replace(ArrayList<Servicio> services){
        this.servicios=services;
        notifyDataSetChanged();
    }

    @Override
    public ServiceViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        final View v = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.service_item, viewGroup, false
        );
        return new ServiceViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ServiceViewHolder holder, int position) {
        Servicio s = servicios.get(position);
        holder.setTxtNombreCliente(String.valueOf(s.getCliente()));
        Date date = s.getFecha();
        dateUtils = new DateUtils(date);
        String strDate = dateUtils.getNow();
        holder.setTxtFecha(strDate);
        holder.setTxtCosto(String.valueOf(s.getCosto()));
    }

    @Override
    public int getItemCount() {
        return servicios.size();
    }

    public static class ServiceViewHolder extends RecyclerView.ViewHolder{
        public TextView txtNombreCliente;
        public TextView txtFecha;
        public TextView txtCosto;

        public ServiceViewHolder(View v){
            super(v);
            txtNombreCliente = (TextView) v.findViewById(R.id.txtNameClient);
            txtFecha = (TextView) v.findViewById(R.id.txtDateService);
            txtCosto = (TextView) v.findViewById(R.id.txtCosto);
        }

        public void setTxtNombreCliente(String nombreCliente){
            this.txtNombreCliente.setText(nombreCliente);
        }

        public void setTxtFecha(String date){
            this.txtFecha.setText(date);
        }

        public void setTxtCosto(String costo){
            this.txtCosto.setText("$"+costo);
        }
    }

}
