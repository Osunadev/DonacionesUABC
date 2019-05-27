package com.example.donacionesuabc.ActivitiesLoggedIn.PaqueteDonaciones;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.donacionesuabc.Articulo;
import com.example.donacionesuabc.CustomAdapter;
import com.example.donacionesuabc.CustomItems;
import com.example.donacionesuabc.MainActivity;
import com.example.donacionesuabc.R;

import java.util.ArrayList;

public class ModificarDonacion extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    Articulo articulo;
    private Spinner spinnerFacultad;
    private Spinner spinnerCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_donacion);

        /**
         * Se toman los datos de articulo y se escriben en los campos correspondientes
         */

        articulo = (Articulo) getIntent().getParcelableExtra("articulo");

        ImageView imageView = (ImageView) findViewById(R.id.modFotoArticulo);
        TextView titulo = (TextView) findViewById(R.id.modTxtBoxNombre);
        TextView descripcion = (TextView) findViewById(R.id.modTxtBoxDescripcion);
        TextView contactos = (TextView) findViewById(R.id.modTxtBoxEmail);
        imageView.setImageResource(articulo.getImgFoto());
        titulo.setText(articulo.getTitulo());
        descripcion.setText(articulo.getDescripcion());
        contactos.setText(articulo.getUserData());

        /**
         * Se hacen los spinners y se llenan con los datos necesarios
         */
        spinnerFacultad=findViewById(R.id.modSpinnerFacultad);
        spinnerCategoria=findViewById(R.id.modSpinnerCategoria);

        //Crear la lista del spinner de facultad
        ArrayList<CustomItems> facultades=new ArrayList<>();
        facultades.add(new CustomItems("Seleccione Facultad",R.drawable.facultades));
        facultades.add(new CustomItems("Artes",R.drawable.artes));
        facultades.add(new CustomItems("Ciencias Quimicas e Ingenieria",R.drawable.ingenieria));
        facultades.add(new CustomItems("Contaduria y Administracion",R.drawable.administracion));
        facultades.add(new CustomItems("Deportes",R.drawable.deportes));
        facultades.add(new CustomItems("Derecho",R.drawable.derecho));
        facultades.add(new CustomItems("Economia y Relaciones Internacionales",R.drawable.economia));
        facultades.add(new CustomItems("Humanidades y Ciencias Sociales",R.drawable.humanidades));
        facultades.add(new CustomItems("Idiomas",R.drawable.idiomas));
        facultades.add(new CustomItems("Medicina y Psicologia",R.drawable.medicina));
        facultades.add(new CustomItems("Odontologia",R.drawable.odontologia));
        facultades.add(new CustomItems("Turismo",R.drawable.turismo));
        facultades.add(new CustomItems("Investigaciones Historicas",R.drawable.historia));

        //Crear la lista del spinner de categorias
        ArrayList<CustomItems> categorias=new ArrayList<>();
        categorias.add(new CustomItems("Seleccione Categoria",R.drawable.categorias));
        categorias.add(new CustomItems("Libros",R.drawable.libros));
        categorias.add(new CustomItems("Ropa",R.drawable.ropa));
        categorias.add(new CustomItems("Dispositivos Electronicos",R.drawable.electronicos));
        categorias.add(new CustomItems("Utencilios de Artes",R.drawable.utencilios_artes));
        categorias.add(new CustomItems("Instrumentos Musicales",R.drawable.instrumentos_musicales));
        categorias.add(new CustomItems("Materiales de Quimica",R.drawable.materiales_quimica));
        categorias.add(new CustomItems("Articulos Deportivos",R.drawable.deportivos));
        categorias.add(new CustomItems("Articulos Medicos",R.drawable.articulos_medicos));

        //Hacer el adaptador de los spinners
        CustomAdapter customAdapter=new CustomAdapter(this,facultades);
        CustomAdapter customAdapter2=new CustomAdapter(this,categorias);

        if(spinnerFacultad!=null){
            spinnerFacultad.setAdapter(customAdapter);
            spinnerFacultad.setOnItemSelectedListener(this);
        }
        if(spinnerCategoria!=null){
            spinnerCategoria.setAdapter(customAdapter2);
            spinnerCategoria.setOnItemSelectedListener(this);
        }

    }

    /**
     * Funcion para el boton atras
     */
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        CustomItems items=(CustomItems) adapterView.getSelectedItem();
        Toast.makeText(this,items.getSpinnerText(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    public void modBackArrow(View view) {
        finish();
    }

    public void modBackHome(View view) {

    }
}
