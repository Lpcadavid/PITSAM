package com.example.santaellapits.data.dao;

import android.util.Log;

import com.example.santaellapits.data.model.Productos;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductosDao {
    private static final String TAG = "ProductosDao";
    private static final String COLLECTION_NAME = "productos";

    private final FirebaseFirestore db;

    public ProductosDao(FirebaseFirestore db){
        this.db = db;
    }

    public void insert(Productos productos, OnSuccessListener<DocumentReference> successListener, OnFailureListener failureListener) {
        Map<String, Object> productosData = new HashMap<>();
        productosData.put("nombre", productos.getNombre());
        productosData.put("categoria", productos.getCategoria());
        productosData.put("cantidad", productos.getCantidad());
        productosData.put("descripcion", productos.getDescripcion());
        productosData.put("precioCompra", productos.getPrecioCompra());
        productosData.put("precioVenta", productos.getPrecioVenta());
        productosData.put("fecha", productos.getFecha());

        db.collection(COLLECTION_NAME)
                .add(productosData)
                .addOnSuccessListener(documentReference -> {
                    Log.d(TAG, "onSuccess: " + documentReference.getId());
                    // Pasa el documentReference al successListener
                    successListener.onSuccess(documentReference);
                })
                .addOnFailureListener(failureListener);
    }

    public void update(String id, Productos productos, OnSuccessListener<Void> successListener, OnFailureListener failureListener) {
        Map<String, Object> productosData = new HashMap<>();
        productosData.put("nombre", productos.getNombre());
        productosData.put("categoria", productos.getCategoria());
        productosData.put("cantidad", productos.getCantidad());
        productosData.put("descripcion", productos.getDescripcion());
        productosData.put("precioCompra", productos.getPrecioCompra());
        productosData.put("precioVenta", productos.getPrecioVenta());
        productosData.put("fecha", productos.getFecha());

        db.collection(COLLECTION_NAME)
                .document(id)
                .update(productosData)
                .addOnSuccessListener(successListener) // successListener ahora es OnSuccessListener<Void>
                .addOnFailureListener(failureListener);
    }

    public void getById(String id, OnSuccessListener<Productos> listener) {
        db.collection(COLLECTION_NAME)
                .document(id)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Productos productos = document.toObject(Productos.class);
                            listener.onSuccess(productos);
                        } else {
                            listener.onSuccess(null);
                        }
                    } else {
                        Log.e(TAG, "onComplete: ", task.getException());
                        listener.onSuccess(null);
                    }
                });
    }

    public void getAllProductos(OnSuccessListener<List<Productos>> listener) {
        db.collection(COLLECTION_NAME)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Productos> productosList = new ArrayList<>();
                    for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        Productos productos = documentSnapshot.toObject(Productos.class);
                        productosList.add(productos);
                    }
                    listener.onSuccess(productosList);
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "onFailure: ", e);
                    listener.onSuccess(null); // Considera usar una callback diferente para el fallo
                });
    }

    public void delete(String id, OnSuccessListener<Void> successListener, OnFailureListener failureListener) {
        db.collection(COLLECTION_NAME)
                .document(id)
                .delete()
                .addOnSuccessListener(successListener) // successListener ahora es OnSuccessListener<Void>
                .addOnFailureListener(failureListener);
    }
}