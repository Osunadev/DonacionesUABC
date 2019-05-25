package com.example.donacionesuabc;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Articulo  implements Parcelable{
    String titulo;
    int imgFoto;
    String descripcion;
    String facultad;
    String userData;

    @Override
    public int describeContents() {
        return hashCode();
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.titulo);
            dest.writeInt(this.imgFoto);
            dest.writeString(this.descripcion);
            dest.writeString(this.facultad);
            dest.writeString(this.userData);
    }

    /**used just for reading object from parcel*/
    public Articulo(Parcel parcel){
        this.titulo = parcel.readString();
        this.imgFoto = parcel.readInt();
        this.descripcion = parcel.readString();
        this.facultad = parcel.readString();
        this.userData = parcel.readString();
    }

    public static final Parcelable.Creator<Articulo> CREATOR = new Parcelable.Creator<Articulo>(){

        @Override
        public Articulo createFromParcel(Parcel parcel) {
            return new Articulo(parcel);
        }

        @Override
        public Articulo[] newArray(int size) {
            return new Articulo [0];
        }
    };

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
