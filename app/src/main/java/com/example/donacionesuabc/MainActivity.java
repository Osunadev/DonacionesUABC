package com.example.donacionesuabc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

        /*mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = firebaseAuth.getCurrentUser();

                // si tenemos un usuario con la sesion iniciada
                if (user != null) {
                    Intent intent = new Intent(MainActivity.this, MenuLoggedActivity.class);
                    startActivity(intent);
                    MainActivity.this.finish();
                }
            }
        };*/
    }

    /*@Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();

        mAuth.removeAuthStateListener(mAuthListener);
    }*/

    public void logInActivity(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}