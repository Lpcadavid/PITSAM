package com.example.santaellapits;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.santaellapits.data.dao.ProductosDao;
import com.google.firebase.firestore.FirebaseFirestore;

public class DetalleProductosActivity extends AppCompatActivity {

    private static final String TAG = "DetalleProductosActivity";
    private static final String COLLECTION_NAME = "productos";
    private FirebaseFirestore db;
    private ProductosDao productosDao;

   TextView textViewProductName, textViewProductDes, textViewProductCat, textViewProductCant, textViewPriceC, textViewPriceV, textViewFecha;
    Button buttonUpdateProduct, buttonDeleteProduct;

    private long id;
    private String nombre;
    private String categoria;
    private String descripcion;
    private String cantidad;
    private String precioCompra;
    private String precioVenta;
    private String fecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detalle_productos);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = FirebaseFirestore.getInstance();
        productosDao = new ProductosDao(db);

    }
}