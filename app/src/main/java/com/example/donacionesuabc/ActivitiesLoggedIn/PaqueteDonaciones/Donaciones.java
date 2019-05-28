package com.example.donacionesuabc.ActivitiesLoggedIn.PaqueteDonaciones;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.donacionesuabc.ActivitiesLoggedIn.AdaptadorArticulos;
import com.example.donacionesuabc.Articulo;
import com.example.donacionesuabc.CustomAdapter;
import com.example.donacionesuabc.CustomItems;
import com.example.donacionesuabc.Model.Donacion;
import com.example.donacionesuabc.R;
import com.example.donacionesuabc.VerArticulos;
import com.example.donacionesuabc.VerTodosArticulos;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Donaciones extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

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
        setContentView(R.layout.activity_donaciones);

        // Firebase Realtime Database
        donacionesRef = FirebaseDatabase.getInstance().getReference("Donaciones");

        donacionesRef.orderByKey().limitToLast(5).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    listDonacionesRecientes.add(data.getValue(Donacion.class));
                    //fillDonationFields(articuloDonacion);
                }

                // Vamos a inicializar los adapters con el contenido de las donaciones recuperadas de firebase
                InitializeArticlesAdapters();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(Donaciones.this, "Se ha fallado al cargar los artículos recientes.", Toast.LENGTH_SHORT).show();
            }
        });

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

        /**
         * Este es el boton de atras
         */
        ImageButton back = (ImageButton) findViewById(R.id.backBtn);
        back.setClickable(true);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void InitializeArticlesAdapters() {
        // Vaciando las donaciones obtenidas de Firebase en la lista de articulos recientes
        for (Donacion donacion: listDonacionesRecientes) {
            // Aqui falta pasar el Url de la imagen para mostrarla en picasso, sin embargo nos muestra warnings
            listItemsRecientes.add(new Articulo(donacion.getArticleName(), R.drawable.ic_active_publications,
                    donacion.getDescription()+"\n"+donacion.getCelular(), donacion.getFacultyName(), donacion.getEmail(),
                    donacion.getImageUrl(), donacion.getCategory()));
        }

        /* Se inicializan los adaptadores */
        lvItems = findViewById(R.id.listaRecientes);
        adaptadorArticulos = new AdaptadorArticulos(Donaciones.this,listItemsRecientes);
        lvItems.setAdapter(adaptadorArticulos);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        CustomItems items=(CustomItems) adapterView.getSelectedItem();
        Toast.makeText(this,items.getSpinnerText(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    /**
     * Esta funcion hace Intent a la activity en la que el usuario sube un articulo para donar
     * Este Intent va en el boton "+"
     */
    public void donarArticulo(View view){
        Intent actDonar = new Intent(this, SubirDonacion.class );
        startActivity(actDonar);
    }

    public void TodosArticulosDonaciones(View view) {
        Intent intent = new Intent(this, VerTodosArticulos.class);
        startActivity(intent);
    }
}
