package com.claudia.misalon.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.claudia.misalon.BO.Producto;
import com.claudia.misalon.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaspar on 26/07/15.
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductoViewHolder> {

    public List<Producto> productos;
    static Context context;

    public ProductAdapter(Context ctx){
        this.context = ctx;
        this.productos=new ArrayList<>();
    }


    public void replace(ArrayList<Producto> products){
        this.productos = products;
        notifyDataSetChanged();
    }

    @Override
    public ProductoViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        final View v = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.product_item, viewGroup, false
        );
        return new ProductoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ProductoViewHolder holder, int position) {
        Producto  p = productos.get(position);
        holder.setTxtNombreProducto(p.getNombre());
        holder.setTxtPrecioProducto(String.valueOf(p.getPrecio()));
    }

    @Override
    public int getItemCount() {
       return  productos.size();
    }

    public static class ProductoViewHolder extends RecyclerView.ViewHolder {
        public TextView txtNombreProducto;
        public TextView txtPrecioProducto;

        public ProductoViewHolder(View v) {
            super(v);
            txtNombreProducto = (TextView) v.findViewById(R.id.txtNameDetail);
            txtPrecioProducto = (TextView) v.findViewById(R.id.txtPrecioDetail);
        }

        public void setTxtNombreProducto(String nombre){
            this.txtNombreProducto.setText(nombre);
        }

        public void setTxtPrecioProducto(String precio){
            this.txtPrecioProducto.setText("$"+precio);
        }
    }



}
