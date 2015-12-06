package com.claudia.misalon.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.claudia.misalon.BO.Cita;
import com.claudia.misalon.R;
import com.claudia.misalon.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by gaspar on 15/11/15.
 */
public class CitaAdapter extends RecyclerView.Adapter<CitaAdapter.CitaViewHolder>{
    public List<Cita> citas;
    static Context context;
    DateUtils dateUtils;

    public CitaAdapter(Context ctx){
        this.context = ctx;
        this.citas = new ArrayList<>();
    }


    public void replace(ArrayList<Cita> clientes){
        this.citas=clientes;
        notifyDataSetChanged();
    }

    @Override
    public CitaViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        final View v = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.cita_item, viewGroup, false
        );
        return new CitaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CitaViewHolder holder, int position) {
        Cita c = citas.get(position);
        holder.setTxtNombreCliente(c.getNombre());
        Date d = c.getFecha();
        dateUtils= new DateUtils(d);
        holder.setTxtFechaCita(dateUtils.getNow());
    }

    @Override
    public int getItemCount() {
        return citas.size();
    }

    public class CitaViewHolder extends RecyclerView.ViewHolder{

        TextView txtNombreCliente;
        TextView txtFechaCita;

        public CitaViewHolder(View v) {
            super(v);
            txtNombreCliente = (TextView) v.findViewById(R.id.txtNameCita);
            txtFechaCita = (TextView) v.findViewById(R.id.txtDateCite);
        }

        public void  setTxtNombreCliente(String nombreCliente){
            txtNombreCliente.setText(nombreCliente);
        }

        public void  setTxtFechaCita(String fechaCita){
            txtFechaCita.setText(fechaCita);
        }
    }
}
