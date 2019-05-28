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
import com.example.donacionesuabc.Model.Donacion;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class VerArticulos extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spinnerFacultad;
    private Spinner spinnerCategoria;
    private ListView lvItems;
    private AdaptadorArticulos adaptadorArticulos;
    private ArrayList<Articulo> listItemsRecientes = new ArrayList<>();
    private ArrayList<Donacion> listDonacionesRecientes = new ArrayList<>();

    // Firebase realtime database
    DatabaseReference donacionesRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_articulos);

        // Firebase Realtime Database
        donacionesRef = FirebaseDatabase.getInstance().getReference("Donaciones");

        donacionesRef.orderByKey().limitToLast(5).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Este post de stack overflow me ayudo a debbugear mi codigo porque como la clase Donacion no tenia un constructor vacio, entonces me marcaba error
                // porque DataSnapshot.getValue(Class<T> valueType) debe recibir una clase con un constructor vacio:
                // https://stackoverflow.com/questions/37830692/parsing-from-datasnapshot-to-java-class-in-firebase-using-getvalue

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                     listDonacionesRecientes.add(data.getValue(Donacion.class));
                    //fillDonationFields(articuloDonacion);
                }

                // Vamos a inicializar los adapters con el contenido de las donaciones recuperadas de firebase
                InitializeArticlesAdapters();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(VerArticulos.this, "Se ha fallado al cargar los artículos recientes.", Toast.LENGTH_SHORT).show();
            }
        });


        /** Se inicializan los apinners */
        spinnerFacultad=findViewById(R.id.modSpinnerFacultad);
        spinnerCategoria=findViewById(R.id.modSpinnerCategoria);

        //Crear la lista del spinner de facultad
        ArrayList<CustomItems> facultades=new ArrayList<>();
        facultades.add(new CustomItems("Seleccione Facultad",R.drawable.facultades));
        facultades.add(new CustomItems("Artes",R.drawable.artes));
        facultades.add(new CustomItems("Ciencias Químicas e Ingenieria",R.drawable.ingenieria));
        facultades.add(new CustomItems("Contaduría y Administración",R.drawable.administracion));
        facultades.add(new CustomItems("Deportes",R.drawable.deportes));
        facultades.add(new CustomItems("Derecho",R.drawable.derecho));
        facultades.add(new CustomItems("Economía y Relaciones Internacionales",R.drawable.economia));
        facultades.add(new CustomItems("Humanidades y Ciencias Sociales",R.drawable.humanidades));
        facultades.add(new CustomItems("Idiomas",R.drawable.idiomas));
        facultades.add(new CustomItems("Medicina y Psicología",R.drawable.medicina));
        facultades.add(new CustomItems("Odontología",R.drawable.odontologia));
        facultades.add(new CustomItems("Turismo",R.drawable.turismo));
        facultades.add(new CustomItems("Investigaciones Históricas",R.drawable.historia));

        //Crear la lista del spinner de categorias
        ArrayList<CustomItems> categorias=new ArrayList<>();
        categorias.add(new CustomItems("Seleccione Categoría",R.drawable.categorias));
        categorias.add(new CustomItems("Libros",R.drawable.libros));
        categorias.add(new CustomItems("Ropa",R.drawable.ropa));
        categorias.add(new CustomItems("Dispositivos Electrónicos",R.drawable.electronicos));
        categorias.add(new CustomItems("Utencilios de Artes",R.drawable.utencilios_artes));
        categorias.add(new CustomItems("Instrumentos Musicales",R.drawable.instrumentos_musicales));
        categorias.add(new CustomItems("Materiales de Química",R.drawable.materiales_quimica));
        categorias.add(new CustomItems("Artículos Deportivos",R.drawable.deportivos));
        categorias.add(new CustomItems("Artículos Médicos",R.drawable.articulos_medicos));

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

    private void InitializeArticlesAdapters() {
        // Vaciando las donaciones obtenidas de Firebase en la lista de articulos recientes
        for (Donacion donacion: listDonacionesRecientes) {
            // Aqui falta pasar el Url de la imagen para mostrarla en picasso, sin embargo nos muestra warnings
            listItemsRecientes.add(new Articulo(donacion.getArticleName(), R.drawable.ic_active_publications,
                    donacion.getDescription(), donacion.getFacultyName(), donacion.getEmail()+"\n"+donacion.getCelular(),
                    donacion.getImageUrl(), donacion.getCategory()));
        }

        /* Se inicializan los adaptadores */
        lvItems = findViewById(R.id.listaRecientes);
        adaptadorArticulos = new AdaptadorArticulos(VerArticulos.this,listItemsRecientes);
        lvItems.setAdapter(adaptadorArticulos);
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
     ** recibe un objeto articulo, como minimo debe recibir
     **   una imagen y un titulo, caso contrario  vease dicha clase
     ***/
    public void addRecentItems(Articulo a){
        this.listItemsRecientes.add(a);
    }

    public void VerTodosArticulos(View view) {
        startActivity(new Intent(this, VerTodosArticulos.class));
    }
}
