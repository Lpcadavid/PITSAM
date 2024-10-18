package com.example.santaellapits.data.model;

public class Productos {
    private long id;
    private String nombre;
    private String categoria;
    private String descripcion;
    private String cantidad;
    private String precioCompra;
    private String precioVenta;
    private String fecha;

    public Productos() {
    }

    public Productos(String nombre, String categoria, String descripcion, String cantidad, String precioCompra, String precioVenta, String fecha) {
    this.nombre = nombre;
    this.categoria = categoria;
    this.descripcion = descripcion;
    this.cantidad = cantidad;
    this.precioCompra = precioCompra;
    this.precioVenta = precioVenta;
    this.fecha = fecha;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(String precioCompra) {
        this.precioCompra = precioCompra;
    }

    public String getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(String precioVenta) {
        this.precioVenta = precioVenta;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
