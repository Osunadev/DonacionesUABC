package com.example.donacionesuabc.Firebase_backend;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.donacionesuabc.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainFirebaseActivity extends AppCompatActivity {

    Button btnArticulos;
    Button logInButton;
    TextView registroTxt;
    TextView titleApp;

    FirebaseAuth mAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_firebase);

        btnArticulos = (Button) findViewById(R.id.verArticulosBtn);
        logInButton = (Button) findViewById(R.id.loginBtn);
        registroTxt = (TextView) findViewById(R.id.registroTxt);
        titleApp = (TextView) findViewById(R.id.menuTitleTxt);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        // Si ya ha iniciado sesion el usuario
        if (user != null) {
            Intent intent = new Intent(MainFirebaseActivity.this, MenuLoggedFirebaseActivity.class);
            startActivity(intent);
            MainFirebaseActivity.this.finish();
        }

    }

    public void logInActivity(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}