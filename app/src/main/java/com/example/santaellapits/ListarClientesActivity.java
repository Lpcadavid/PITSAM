package com.example.santaellapits;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.santaellapits.data.adapter.ClienteAdapter;
import com.example.santaellapits.data.adapter.ProductoAdapter;
import com.example.santaellapits.data.dao.ClienteDao;
import com.example.santaellapits.data.dao.ProductosDao;
import com.google.firebase.firestore.FirebaseFirestore;

public class ListarClientesActivity extends AppCompatActivity {

    private ClienteDao clienteDao;
    private RecyclerView recyclerView;
    private ClienteAdapter adapter;
    private Button btnMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_listar_clientes);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializar Firebase Firestore y ProductosDao
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        clienteDao = new ClienteDao(db);

        // Obtener la referencia al RecyclerView
        recyclerView = findViewById(R.id.recyclerView);

        // Obtener la referencia al botón del menú
        btnMenu = findViewById(R.id.btnMenu);

        // Configurar el OnClickListener para el botón del menú
        btnMenu.setOnClickListener(view -> {
            Intent intent = new Intent(ListarClientesActivity.this, MainActivity.class);
            startActivity(intent);
        });

        // Obtener todos los productos de Firebase Firestore
        clienteDao.getAllClientes(clientesList -> {
            if (clientesList != null) {
                // Crear el adaptador y configurarlo con la lista de productos
                adapter = new ClienteAdapter(clientesList);

                // Configurar el OnItemClickListener para el adaptador
                adapter.setOnItemClickListener(Clientes -> {
                    Intent intent = new Intent(ListarClientesActivity.this, DetalleClientesActivity.class);
                    // Pasar los datos del producto al intent
                    intent.putExtra("Clientes_ID", Clientes.getId());
                    intent.putExtra("Nombre", Clientes.getName());
                    intent.putExtra("E-mail", Clientes.getEmail());
                    intent.putExtra("Edad", Clientes.getEdad());
                    intent.putExtra("Ciudad", Clientes.getCiudad());

                    // Iniciar la actividad DetalleProductosActivity
                    startActivity(intent);
                });

                // Configurar el RecyclerView con el adaptador y el LinearLayoutManager
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
            } else {
                // Manejar la lista vacía (por ejemplo, mostrar un mensaje al usuario)
                // ...
            }
        });
    }
}