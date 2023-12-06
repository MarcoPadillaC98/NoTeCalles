package com.example.notecalles.model;

public class Publicacion {

    private String tipo,fecha,hecho,username;
    private Double latitud,longitud;

    public Publicacion() {
        super();
    }

    public Publicacion(String tipo, String fecha,String hecho, String username, Double latitud, Double longitud) {
        this.tipo = tipo;
        this.fecha = fecha;
        this.hecho = hecho;
        this.username = username;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFecha() {
        return fecha;
    }
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHecho() {
        return hecho;
    }

    public void setHecho(String hecho) {
        this.hecho = hecho;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }
}
