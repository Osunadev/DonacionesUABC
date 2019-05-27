package com.example.donacionesuabc.ActivitiesLoggedIn.PaqueteDonaciones;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.donacionesuabc.CustomAdapter;
import com.example.donacionesuabc.CustomItems;
import com.example.donacionesuabc.MainActivity;
import com.example.donacionesuabc.R;

import java.util.ArrayList;

public class SubirDonacion extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private static final int PICK_IMAGE = 100;
    private Spinner spinnerFacultad;
    private Spinner spinnerCategoria;
    private ImageView item_img;
    private ImageButton back;
    private ImageButton home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subir_donacion);

        item_img = (ImageView) findViewById(R.id.modFotoArticulo);
        back = (ImageButton) findViewById(R.id.backBtn2);
        home = (ImageButton) findViewById(R.id.homeBtn2);

        /**
         * Se inicializan los spinners
         */
        spinnerFacultad = findViewById(R.id.subSpinnerFacultad);
        spinnerCategoria = findViewById(R.id.subSpinnerCategoria);
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

    private void pickup_photo(){
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(i,PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            item_img.setImageURI((Uri) data.getData());
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        CustomItems items=(CustomItems) adapterView.getSelectedItem();
        Toast.makeText(this,items.getSpinnerText(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void backHome(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    public void backArrow(View view) {
        finish();
    }

    public void selectPhoto(View view) {
        pickup_photo();
    }
}
