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
import com.example.santaellapits.data.adapter.ProductoAdapter;
import com.example.santaellapits.data.dao.ProductosDao;
import com.google.firebase.firestore.FirebaseFirestore;

public class ListarProductosActivity extends AppCompatActivity {

    private ProductosDao productosDao;
    private RecyclerView recyclerView;
    private ProductoAdapter adapter;
    private Button btnMenuProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_listar_productos);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializar Firebase Firestore y ProductosDao
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        productosDao = new ProductosDao(db);

        // Obtener la referencia al RecyclerView
        recyclerView = findViewById(R.id.recyclerViewPro);

        // Obtener la referencia al botón del menú
        btnMenuProduct = findViewById(R.id.btnMenuProduct);

        // Configurar el OnClickListener para el botón del menú
        btnMenuProduct.setOnClickListener(view -> {
            Intent intent = new Intent(ListarProductosActivity.this, MainActivity.class);
            startActivity(intent);
        });

        // Obtener todos los productos de Firebase Firestore
        productosDao.getAllProductos(productosList -> {
            if (productosList != null) {
                // Crear el adaptador y configurarlo con la lista de productos
                adapter = new ProductoAdapter(productosList);

                // Configurar el OnItemClickListener para el adaptador
                adapter.setOnItemClickListener(productos -> {
                    Intent intent = new Intent(ListarProductosActivity.this, DetalleProductosActivity.class);
                    // Pasar los datos del producto al intent
                    intent.putExtra("Productos_ID", productos.getId());
                    intent.putExtra("Nombre", productos.getNombre());
                    intent.putExtra("Categoria", productos.getCategoria());
                    intent.putExtra("Descripcion", productos.getDescripcion());
                    intent.putExtra("Precio Compra", productos.getPrecioCompra());
                    intent.putExtra("Precio Venta", productos.getPrecioVenta());
                    intent.putExtra("Cantidad", productos.getCantidad());
                    intent.putExtra("Fecha", productos.getFecha());

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