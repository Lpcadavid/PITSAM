package com.example.santaellapits;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.santaellapits.data.dao.ClienteDao;
import com.example.santaellapits.data.model.Clientes;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class CrearClientesActivity extends AppCompatActivity {

    private Button btnIngresarCLiente, btnActualizarCliente;
    private EditText etName;
    private EditText etEmail;
    private EditText etEdad;
    private EditText etCiudad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_crear_clientes);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //botones
        btnIngresarCLiente = findViewById(R.id.btnIngresarCliente);
        btnActualizarCliente = findViewById(R.id.btnActualizarCliente);

        // inputs
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etEdad = findViewById(R.id.etEdad);
        etCiudad = findViewById(R.id.etCiudad);

        btnIngresarCLiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseFirestore db = FirebaseFirestore.getInstance();

                ClienteDao clienteDao = new ClienteDao(db);
                Clientes clientes = new Clientes();
                clientes.setName(etName.getText().toString());
                clientes.setEmail(etEmail.getText().toString());
                clientes.setEdad(etEdad.getText().toString());
                clientes.setCiudad(etCiudad.getText().toString());

                clienteDao.insert(clientes, new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(CrearClientesActivity.this, "Cliente Creado con Exito",
                                Toast.LENGTH_SHORT).show();
                    }
                }, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("CrearClientesActivity", "Error al insertar el cliente", e);
                        Toast.makeText(CrearClientesActivity.this, "Error al crear el cliente",
                                Toast.LENGTH_SHORT).show();
                    }
                });

                etName.setText("");
                etEmail.setText("");
                etEdad.setText("");
                etCiudad.setText("");
            }
        });
    }
}