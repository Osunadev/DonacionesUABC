package com.example.donacionesuabc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.donacionesuabc.ActivitiesLoggedIn.AdaptadorArticulos;
import com.example.donacionesuabc.ActivitiesLoggedIn.PaqueteDonaciones.PublicacionesActivasDonacion;

import java.util.ArrayList;

public class VerArticulos extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spinnerFacultad;
    private Spinner spinnerCategoria;
    private ListView lvItems;
    private AdaptadorArticulos adaptadorArticulos;
    private ArrayList<Articulo> listItems = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_articulos);
        /**
         * Aqui no estoy seguro de porque se repiten tanto
         */
        listItems.add(new Articulo("Charizard", R.drawable.charizard, "Pokemon fuego/volador", "FCQI", "isaachctj@hotmail.com"));
        listItems.add(new Articulo(R.drawable.yveltal, "Yveltal"));
        listItems.add(new Articulo(R.drawable.silvally, "Silvally"));
        listItems.add(new Articulo("Charizard", R.drawable.charizard, "Pokemon fuego/volador", "FCQI", "isaachctj@hotmail.com"));
        listItems.add(new Articulo(R.drawable.yveltal, "Yveltal"));
        listItems.add(new Articulo(R.drawable.silvally, "Silvally"));
        listItems.add(new Articulo("Charizard", R.drawable.charizard, "Pokemon fuego/volador", "FCQI", "isaachctj@hotmail.com"));
        listItems.add(new Articulo(R.drawable.yveltal, "Yveltal"));
        listItems.add(new Articulo(R.drawable.silvally, "Silvally"));
        /** Se inicializan los apinners y adaptadores */
        spinnerFacultad=findViewById(R.id.modSpinnerFacultad);
        spinnerCategoria=findViewById(R.id.modSpinnerCategoria);

        lvItems = findViewById(R.id.listaRecientes);
        adaptadorArticulos = new AdaptadorArticulos(this,listItems);
        lvItems.setAdapter(adaptadorArticulos);

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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        CustomItems items=(CustomItems) adapterView.getSelectedItem();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void menuPrincipal(View view){
        finish();
    }


    /*
     ** Esta funcion agrega elemntos al list view reciente,
     ** recive un objeto articulo, como minimo debe recibir
     **   una imagen y un titulo, caso contrario  vease dicha clase
     ***/
    public void addRecentItems(Articulo a){
        this.listItems.add(a);
    }

    /**
     * Funcion que hace Intent para la vista de ver mis Donaciones
     */
    public void misDonaciones(View view){

    }

}
