package com.example.donacionesuabc;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class vista_activity extends AppCompatActivity {

    private ListView Lview;
    private Articulo articulo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_activity);

        articulo = (Articulo) getIntent().getParcelableExtra("articulo");

        ImageView imageView = (ImageView) findViewById(R.id.imgItem2);
        TextView titulo = (TextView) findViewById(R.id.itemTittle2);
        TextView facultad = (TextView) findViewById(R.id.itemLocation2);
        TextView descripcion = (TextView) findViewById(R.id.itemDescription2);
        TextView contactos = (TextView) findViewById(R.id.userData2);
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

    }

}
