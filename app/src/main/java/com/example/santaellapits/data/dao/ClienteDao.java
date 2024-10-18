package com.example.santaellapits.data.dao;

import android.util.Log;

import com.example.santaellapits.data.model.Clientes;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClienteDao {

    private static final String TAG = "ClienteDao";
    private static final String COLLECTION_NAME = "clientes";

    private final FirebaseFirestore db;

    public ClienteDao(FirebaseFirestore db) {
        this.db = db;
    }

    public void insert(Clientes clientes, OnSuccessListener<DocumentReference> successListener, OnFailureListener failureListener) {
        Map<String, Object> clientesData = new HashMap<>();
        clientesData.put("name", clientes.getName());
        clientesData.put("email", clientes.getEmail());
        clientesData.put("edad", clientes.getEdad());
        clientesData.put("ciudad", clientes.getCiudad());


        db.collection(COLLECTION_NAME)
                .add(clientesData)
                .addOnSuccessListener(documentReference -> {
                    Log.d(TAG, "onSuccess: " + documentReference.getId());
                    successListener.onSuccess(documentReference); // Pass documentReference
                })
                .addOnFailureListener(failureListener); // Handle failures
    }

    public void update(String id, Clientes clientes, OnSuccessListener<Void> successListener, OnFailureListener failureListener) {
        Map<String, Object> clientesData = new HashMap<>();
        clientesData.put("name", clientes.getName()); // Lowercase field name
        clientesData.put("email", clientes.getEmail()); // Lowercase field name
        clientesData.put("edad", clientes.getEdad()); // Lowercase field name
        clientesData.put("ciudad", clientes.getCiudad()); // Lowercase field name

        db.collection(COLLECTION_NAME)
                .document(id)
                .update(clientesData)
                .addOnSuccessListener(successListener) // OnSuccessListener<Void>
                .addOnFailureListener(failureListener); // Handle failures
    }

    public void getById(String id, OnSuccessListener<Clientes> listener) {
        db.collection(COLLECTION_NAME)
                .document(id)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Clientes clientes = document.toObject(Clientes.class);
                            listener.onSuccess(clientes);
                        } else {
                            listener.onSuccess(null);
                        }
                    } else {
                        Log.e(TAG, "onComplete: ", task.getException());
                        listener.onSuccess(null);
                    }
                });
    }

    public void getAllClientes(OnSuccessListener<List<Clientes>> listener) {
        db.collection(COLLECTION_NAME) // Using COLLECTION_NAME constant
                .get().addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Clientes> clientesList = new ArrayList<>();
                    for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        Clientes clientes = documentSnapshot.toObject(Clientes.class);
                        clientesList.add(clientes);
                    }
                    listener.onSuccess(clientesList);
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "onFailure: ", e);
                    listener.onSuccess(null); // Consider using a different callback for failure
                });
    }

    public void delete(String id, OnSuccessListener<Void> successListener, OnFailureListener failureListener) {
        db.collection(COLLECTION_NAME)
                .document(id)
                .delete()
                .addOnSuccessListener(successListener) // OnSuccessListener<Void>
                .addOnFailureListener(failureListener); // Handle failures
    }
}