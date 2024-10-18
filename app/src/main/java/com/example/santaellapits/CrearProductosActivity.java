package com.example.santaellapits;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.santaellapits.data.dao.ProductosDao;
import com.example.santaellapits.data.model.Productos;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

public class CrearProductosActivity extends AppCompatActivity {
    private Button btnIngresarProducto, btnActualizarProducto;
    private EditText etNombreP;
    private EditText etCategoria;
    private EditText etCantidad;
    private EditText etDescripcion;
    private EditText etPrecioC;
    private EditText etPrecioV;
    private EditText etFecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_crear_productos);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //botones
        btnIngresarProducto = findViewById(R.id.btnIngresarProducto);
        btnActualizarProducto = findViewById(R.id.btnActualizarProducto);

        // inputs
        etNombreP = findViewById(R.id.etNombreP);
        etCategoria = findViewById(R.id.etCategoria);
        etCantidad = findViewById(R.id.etCantidad);
        etDescripcion = findViewById(R.id.etDescripcion);
        etPrecioC = findViewById(R.id.etPrecioC);
        etPrecioV = findViewById(R.id.etPrecioV);
        etFecha = findViewById(R.id.etFecha);

        etFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        CrearProductosActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                                // Formatear la fecha seleccionada y mostrarla en el EditText
                                String selectedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                                etFecha.setText(selectedDate);
                            }
                        },
                        year, month, day);
                datePickerDialog.show();
            }
        });


        btnIngresarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseFirestore db = FirebaseFirestore.getInstance();

                ProductosDao productosDao = new ProductosDao(db);
                Productos productos = new Productos();
                productos.setNombre(etNombreP.getText().toString());
                productos.setCategoria(etCategoria.getText().toString());
                productos.setCantidad(etCantidad.getText().toString());
                productos.setDescripcion(etDescripcion.getText().toString());
                productos.setPrecioCompra(etPrecioC.getText().toString());
                productos.setPrecioVenta(etPrecioV.getText().toString());
                productos.setFecha(etFecha.getText().toString());

                productosDao.insert(productos, new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(CrearProductosActivity.this, "Producto Creado con Exito",
                                Toast.LENGTH_SHORT).show();
                    }
                }, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("CrearProductosActivity", "Error al insertar el documento", e);
                        Toast.makeText(CrearProductosActivity.this, "Error al crear el producto",
                                Toast.LENGTH_SHORT).show();
                    }
                });
                etNombreP.setText("");
                etCategoria.setText("");
                etCantidad.setText("");
                etDescripcion.setText("");
                etPrecioC.setText("");
                etPrecioV.setText("");
                etFecha.setText("");


            }
        });



    }
}