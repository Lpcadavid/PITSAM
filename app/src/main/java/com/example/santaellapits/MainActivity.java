package com.example.santaellapits;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.santaellapits.data.dao.ClienteDao;
import com.example.santaellapits.data.model.Clientes;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private Button btnInicioClientes;
    private Button btnInicioProductos;
    private Button btnListarClientes;
    private Button btnListarProductos;
    private Button btnUpload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnInicioClientes = (Button) findViewById(R.id.btnInicioClientes);
        btnInicioClientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CrearClientesActivity.class);
                startActivity(intent);
            }
        });

        btnInicioProductos = (Button) findViewById(R.id.btnInicioProductos);
        btnInicioProductos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CrearProductosActivity.class);
                startActivity(intent);
            }
        });

        btnListarClientes = (Button) findViewById(R.id.btnListarClientes);
        btnListarClientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListarClientesActivity.class);
                startActivity(intent);
            }
        });

        btnListarProductos = (Button) findViewById(R.id.btnListarProductos);
        btnListarProductos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListarProductosActivity.class);
                startActivity(intent);
            }
        });

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        btnUpload = findViewById(R.id.btnUpload);


        // Registers a photo picker activity launcher in single-select mode.
        ActivityResultLauncher<PickVisualMediaRequest> pickMedia = registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
            // Callback is invoked after the user selects a media item or closes the
            // photo picker.
            if (uri != null) {
                Log.d("PhotoPicker", "Selected URI: " + uri);

                // Get a reference to the file to be uploaded
                StorageReference fileRef = storageRef.child("images/" + UUID.randomUUID().toString() + ".jpg"); // You can change the folder name and file extension if you want

                // Upload the file
                UploadTask uploadTask = fileRef.putFile(uri);

                // Observe the progress of the upload
                uploadTask.addOnSuccessListener((taskSnapshot) -> {
                    // When the upload is successful, get the download URL
                    taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Log.d("PhotoPicker", "Download URL: " + uri);
                        }
                    });
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("PhotoPicker", "Upload failed", e);
                    }
                });
            } else {
                Log.d("PhotoPicker", "No media selected");
            }
        });


        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Launch the photo picker and let the user choose only images.
                pickMedia.launch(new PickVisualMediaRequest.Builder()
                        .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                        .build());
            }
        });

    }
}