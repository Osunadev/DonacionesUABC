package com.example.donacionesuabc.Model;

public class ManagePublicationName {

    // Metodo para formar el nombre que se le asignara a la imagen de la publicacion de donacion
    public static String imageFormatName(String user_id, String donation_title) {
        String image_format =  user_id+"_"+donation_title.split(" ")[0]+ System.currentTimeMillis();
        return  image_format;
    }

}
