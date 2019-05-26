package com.example.donacionesuabc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class EstadoDonacion extends AppCompatActivity {
    private Articulo articulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estado_donacion);

        articulo = (Articulo) getIntent().getParcelableExtra("articulo");

        TextView nombre = (TextView) findViewById(R.id.nombreArt);
        nombre.setText(articulo.getTitulo());
    }

    public void goBack(View view){
        finish();
    }

    public void modificar(View view){
        Intent i  = new Intent(this,ModificarDonacion.class);
        i.putExtra("articulo",articulo);
        startActivity(i);
    }
}
