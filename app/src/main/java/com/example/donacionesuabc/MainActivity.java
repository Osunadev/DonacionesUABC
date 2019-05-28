package com.example.donacionesuabc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.support. v7.widget.Toolbar;

import com.example.donacionesuabc.ActivitiesLoggedIn.LogInActivity;
import com.example.donacionesuabc.ActivitiesLoggedIn.MenuLoggedActivity;
import com.example.donacionesuabc.ActivitiesLoggedIn.RegisterActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    Button btnArticulos;
    Button logInButton;
    TextView registroTxt;
    TextView titleApp;

    FirebaseAuth mAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnArticulos = (Button) findViewById(R.id.verArticulosBtn);
        logInButton = (Button) findViewById(R.id.loginBtn);
        registroTxt = (TextView) findViewById(R.id.registroTxt);
        titleApp = (TextView) findViewById(R.id.menuTitleTxt);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        // Si ya ha iniciado sesion el usuario
        if (user != null) {
            Intent intent = new Intent(MainActivity.this, MenuLoggedActivity.class);
            startActivity(intent);
            MainActivity.this.finish();
        }

    }
    public void logInUser(View view) {
        // Por ahorita este boton nos dirigira directamente a la pantalla de activity_main de usuario Logged In
        Intent intent = new Intent(this, LogInActivity.class);
        startActivity(intent);
    }


    public void registerUser(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    /****/
    public void menu(View view){
        startActivity(new Intent(this, VerArticulos.class));
    }
}
