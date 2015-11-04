package com.claudia.misalon.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.claudia.misalon.BO.Cliente;
import com.claudia.misalon.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaspar on 01/11/15.
 */
public class ClienteAdapter extends  RecyclerView.Adapter<ClienteAdapter.ClienteViewHolder> {

    public List<Cliente> clientes;
    static Context context;

    public ClienteAdapter(Context ctx){
        this.context=ctx;
        this.clientes = new ArrayList<>();
    }

    public void replace(ArrayList<Cliente> clientes){
        this.clientes=clientes;
        notifyDataSetChanged();
    }

    @Override
    public ClienteViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        final View v = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.client_item, viewGroup, false
        );
        return new ClienteViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ClienteViewHolder holder, int position) {
        Cliente c = clientes.get(position);
        holder.setTxtNombreCliente(c.getNombre());
        holder.setTxtTelefonoCliente(c.getTelefono());
    }

    @Override
    public int getItemCount() {
        return clientes.size();
    }

    public class ClienteViewHolder extends RecyclerView.ViewHolder{
        public TextView txtNombreCliente;
        public TextView txtTelefonoCliente;

        public ClienteViewHolder(View v){
            super(v);
            txtNombreCliente = (TextView) v.findViewById(R.id.txtNameClient);
            txtTelefonoCliente = (TextView) v.findViewById(R.id.txtTelClient);
        }

        public void setTxtNombreCliente(String nombreCliente){
            this.txtNombreCliente.setText(nombreCliente);
        }

        public void setTxtTelefonoCliente(String telefonoCliente){
            this.txtTelefonoCliente.setText(telefonoCliente);
        }
    }
}
