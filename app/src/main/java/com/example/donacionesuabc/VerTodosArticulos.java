package com.example.donacionesuabc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.donacionesuabc.ActivitiesLoggedIn.AdaptadorArticulos;
import com.example.donacionesuabc.Model.Donacion;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class VerTodosArticulos extends AppCompatActivity {

    ImageButton backBtn;
    EditText barraBusqueda;
    ListView resultados;
    private AdaptadorArticulos adaptadorArticulos;
    private ArrayList<Articulo> listItems = new ArrayList<>();
    private ArrayList<Donacion> listDonaciones = new ArrayList<>();

    // Firebase realtime database
    DatabaseReference donacionesRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_todos_articulos);

        // Firebase Realtime Database
        donacionesRef = FirebaseDatabase.getInstance().getReference("Donaciones");

        donacionesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    listDonaciones.add(data.getValue(Donacion.class));
                }

                // Vamos a inicializar los adapters con el contenido de las donaciones recuperadas de firebase
                InitializeArticlesAdapters();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(VerTodosArticulos.this, "Se ha fallado al cargar los todos los artículos de donación.", Toast.LENGTH_SHORT).show();
            }
        });

        backBtn = (ImageButton) findViewById(R.id.backBtnResultados);
        barraBusqueda = (EditText) findViewById(R.id.barraBusquedaResultados);
    }

    private void InitializeArticlesAdapters() {
        // Vaciando las donaciones obtenidas de Firebase en la lista de articulos recientes
        for (Donacion donacion: listDonaciones) {
            // Aqui falta pasar el Url de la imagen para mostrarla en picasso, sin embargo nos muestra warnings
            listItems.add(new Articulo(donacion.getArticleName(), R.drawable.ic_active_publications,
                    donacion.getDescription(), donacion.getFacultyName(), donacion.getEmail()+"\n"+donacion.getCelular(),
                    donacion.getImageUrl(), donacion.getCategory()));
        }

        /* Se inicializan los adaptadores */
        resultados = (ListView) findViewById(R.id.listaResultados);
        adaptadorArticulos = new AdaptadorArticulos(this,listItems);
        resultados.setAdapter(adaptadorArticulos);
    }

    public void backButtonTodosArticulos(View view) {
        finish();
    }
}
