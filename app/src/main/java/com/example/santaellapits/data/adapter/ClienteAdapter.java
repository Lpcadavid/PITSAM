package com.example.santaellapits.data.adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.santaellapits.R;
import com.example.santaellapits.data.model.Clientes;
import java.util.List;

public class ClienteAdapter extends RecyclerView.Adapter<ClienteAdapter.ViewHolder>{
    private List<Clientes> clientes;

    public ClienteAdapter(List<Clientes> clientes) {
        this.clientes = clientes;
    }

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Clientes clientes);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ClienteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_clientes, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClienteAdapter.ViewHolder holder, int position) {
        Clientes clientes = this.clientes.get(position);

        holder.clientesName.setText(clientes.getName());
        holder.clientesEmail.setText(clientes.getEmail());
        holder.clientesEdad.setText(clientes.getEdad());
        holder.clientesCiudad.setText(clientes.getCiudad());


        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(clientes);
            }
        });
    }

    @Override
    public int getItemCount() {
        return clientes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView clientesName;
        public TextView clientesEmail;
        public TextView clientesEdad;
        public TextView clientesCiudad;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            clientesName = itemView.findViewById(R.id.clientesName);
            clientesEmail = itemView.findViewById(R.id.clientesEmail);
            clientesEdad = itemView.findViewById(R.id.clientesEdad);
            clientesCiudad = itemView.findViewById(R.id.clientesCiudad);
        }
    }
}
