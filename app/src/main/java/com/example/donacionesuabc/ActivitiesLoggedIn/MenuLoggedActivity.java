package com.example.donacionesuabc.ActivitiesLoggedIn;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.donacionesuabc.ActivitiesLoggedIn.PaqueteDonaciones.Donaciones;
import com.example.donacionesuabc.ActivitiesLoggedIn.PaqueteDonaciones.PublicacionesActivasDonacion;
import com.example.donacionesuabc.R;

public class MenuLoggedActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    Button donacionesBtn;
    Button intercambiosBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_logged);

        /* Todo esto es para tener listo nuestro Drawer (menu de hamburguesa) */
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();





        /* Componentes del Layout */
        donacionesBtn = (Button) findViewById(R.id.donacionesBtn);
        intercambiosBtn = (Button) findViewById(R.id.intercambiosBtn);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        // Lo que haremos despues de haber presionado estas opciones
        switch(menuItem.getItemId()) {
            case R.id.nav_account:
                Toast.makeText(this, "Mi Cuenta", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_active_publications:
                Toast.makeText(this, "Mis publicaciones activas", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, PublicacionesActivasDonacion.class);
                startActivity(intent);
                break;
            case R.id.nav_request_donations:
                Toast.makeText(this, "Mis Donaciones Solicitadas", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_request_trades:
                Toast.makeText(this, "Mis Intercambios Solicitados", Toast.LENGTH_SHORT).show();
                break;
        }

        // Cerrando el Drawer despues de hacer click
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    // Sobreescribimos este metodo porque queremos que cuando el usuario presione el back button
    // del telefono, solamente cierre el menu de hamburguesa (si es que lo tiene abierto
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);
        else {
            super.onBackPressed();
        }
    }

    public void goToIntercambios(View view) {
    }

    public void goToDonaciones(View view) {
        startActivity(new Intent(this, Donaciones.class));
    }
}
