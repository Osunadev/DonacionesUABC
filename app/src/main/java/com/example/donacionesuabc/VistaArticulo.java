package com.example.donacionesuabc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.donacionesuabc.ActivitiesLoggedIn.MenuLoggedActivity;
import com.squareup.picasso.Picasso;

public class VistaArticulo extends AppCompatActivity {

    private ListView Lview;
    private Articulo articulo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_articulo);

        /**
         * Se toman los datos del articulo que llega de la vista anterior
         * y se escriben en los campos correspondientes
         */
        articulo = (Articulo) getIntent().getParcelableExtra("articulo");

        ImageView imageView = (ImageView) findViewById(R.id.modFotoArticulo);
        TextView titulo = (TextView) findViewById(R.id.nombreArticulo);
        TextView facultad = (TextView) findViewById(R.id.facultadArticulo);
        TextView descripcion = (TextView) findViewById(R.id.modTxtBoxDescripcion);
        TextView contactos = (TextView) findViewById(R.id.contactoArticulo);


        // Aqui en vez de poner este metodo, utilizaremos Picasso para cargar el url de la imagen que pasemos mas adelante
        // dentro del ImageView
        imageView.setImageResource(articulo.getImgFoto());
        titulo.setText(articulo.getTitulo());
        facultad.setText(articulo.getFacultad());
        descripcion.setText(articulo.getDescripcion());
        contactos.setText(articulo.getUserData());

    }

    public void goBack(View view){
        finish();
    }

    public void goHome(View view){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

}
