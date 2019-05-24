package com.example.donacionesuabc;

import java.io.Serializable;

public class Articulo  implements Serializable {
    String titulo;
    int imgFoto;
    String descripcion;
    String facultad;
    String userData;



    public Articulo(int imgFoto, String titulo) {
        this(titulo,imgFoto," "," "," ");
    }

    public Articulo(String titulo, int imgFoto, String descripcion, String facultad, String userData) {
        this.titulo = titulo;
        this.imgFoto = imgFoto;
        this.descripcion = descripcion;
        this.facultad = facultad;
        this.userData = userData;
    }

    public String getFacultad() {
        return facultad;
    }

    public void setFacultad(String facultad) {
        this.facultad = facultad;
    }

    public String getUserData() {
        return userData;
    }

    public void setUserData(String userData) {
        this.userData = userData;
    }

    public String getTitulo(){
        return titulo;
    }

    public void setTitulo(String titulo){
        this.titulo = titulo;
    }

    public String getDescripcion(){
        return descripcion;
    }

    public void setDescripcion(String descripcion){
        this.descripcion=descripcion;
    }

    public int getImgFoto(){
        return imgFoto;
    }

    public void setImgFoto(int imgFoto){
        this.imgFoto = imgFoto;
    }
}
