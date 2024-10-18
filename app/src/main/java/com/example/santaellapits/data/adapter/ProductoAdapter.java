package com.example.santaellapits.data.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.santaellapits.R;
import com.example.santaellapits.data.model.Productos;
import java.util.List;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ViewHolder>{
    private List<Productos> productos;

    public ProductoAdapter(List<Productos> products) {
        this.productos = products;
    }

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Productos productos);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProductoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_productos, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoAdapter.ViewHolder holder, int position) {
        Productos productos = this.productos.get(position);
        holder.productosNombre.setText(productos.getNombre());
        holder.productosCategoria.setText(productos.getCategoria());
        holder.productosDescripcion.setText(productos.getDescripcion());
        holder.productosPrecioCompra.setText(productos.getPrecioCompra());
        holder.productosPrecioVenta.setText(productos.getPrecioVenta());
        holder.productosCantidad.setText(productos.getCantidad());


        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(productos);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView productosNombre;
        public TextView productosCategoria;
        public TextView productosDescripcion;
        public TextView productosPrecioCompra;
        public TextView productosPrecioVenta;
        public TextView productosCantidad;
        public TextView productosFecha;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
           productosNombre = itemView.findViewById(R.id.productosNombre);
           productosCategoria = itemView.findViewById(R.id.productosCategoria);
           productosDescripcion = itemView.findViewById(R.id.productosDescripcion);
           productosPrecioCompra = itemView.findViewById(R.id.productosPrecioCompra);
           productosPrecioVenta = itemView.findViewById(R.id.productosPrecioVenta);
           productosCantidad = itemView.findViewById(R.id.productosCantidad);
           productosFecha = itemView.findViewById(R.id.productosFecha);

        }
    }
}
