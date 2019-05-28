package com.example.donacionesuabc.ActivitiesLoggedIn.PaqueteDonaciones;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

//import AdaptadorDonaciones;
import com.example.donacionesuabc.Articulo;
import com.example.donacionesuabc.CustomItems;
import com.example.donacionesuabc.R;

import java.util.ArrayList;

public class PublicacionesActivasDonacion extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private ListView listaDonaciones;
    private AdaptadorDonaciones adaptadorDonaciones;
    private ArrayList<Articulo> listItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicaciones_activas_donacion);

        /**
         * Se ponen estos "articulos" para pruebas mientras se implementan los buenos
         */
        /*listItems.add(new Articulo("Charizard", R.drawable.charizard, "Pokemon fuego/volador", "FCQI", "isaachctj@hotmail.com"));
        listItems.add(new Articulo("Yveltal", R.drawable.yveltal, "Pokemon legendario que, al extender sus alas y las plumas de la cola, emite un brillo carmesi que absorbe la energía vital de su enemigo", "FCQI", "isaachctj@hotmail.com"));
        listItems.add(new Articulo("Silvally", R.drawable.silvally, "Pokemon artificial que cambia de tipo", "FCQI", "isaachctj@hotmail.com"));
*/
        listaDonaciones = findViewById(R.id.listaDonaciones);
        adaptadorDonaciones = new AdaptadorDonaciones(this,listItems);
        listaDonaciones.setAdapter(adaptadorDonaciones);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        CustomItems items=(CustomItems) adapterView.getSelectedItem();
        Toast.makeText(this,items.getSpinnerText(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void addRecentItems(Articulo a){
        this.listItems.add(a);
    }

    public void backArrowActivePublications(View view) {
        finish();
    }
}
