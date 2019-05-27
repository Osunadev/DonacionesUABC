package com.example.donacionesuabc.ActivitiesLoggedIn.PaqueteDonaciones;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.donacionesuabc.Articulo;
import com.example.donacionesuabc.R;

public class EstadoDonacion extends AppCompatActivity {
    private Articulo articulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estado_donacion);
        /**
         * Toma el articulo enviado por la vista de publicaciones activas
         * y escribe su nombre en la pantalla
         */
        articulo = (Articulo) getIntent().getParcelableExtra("articulo");

        TextView nombre = (TextView) findViewById(R.id.nombreArt);
        nombre.setText(articulo.getTitulo());
    }

    /**
     * Funcion para el boton atras
     */
    public void goBack(View view){
        finish();
    }

    /**
     * Funcion para modificar una donacion
     */
    public void modificar(View view){
        Intent i  = new Intent(this, ModificarDonacion.class);
        i.putExtra("articulo",articulo);
        startActivity(i);
    }
}
