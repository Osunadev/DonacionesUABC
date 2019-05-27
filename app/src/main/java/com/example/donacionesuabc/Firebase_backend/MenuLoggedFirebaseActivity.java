package com.example.donacionesuabc.Firebase_backend;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.donacionesuabc.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MenuLoggedFirebaseActivity extends AppCompatActivity {

    Button donacionesBtn;
    Button intercambiosBtn;
    Button cerrarSesionBtn;
    TextView correoUsuario;

    FirebaseAuth mAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_logged_firebase);

        donacionesBtn = (Button) findViewById(R.id.donacionesBtn);
        intercambiosBtn = (Button) findViewById(R.id.intercambiosBtn);
        cerrarSesionBtn = (Button) findViewById(R.id.signOutBtn);
        correoUsuario = (TextView) findViewById(R.id.userEmailTxt);


        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        correoUsuario.setText(user.getEmail());
    }

    public void changeDonationView(View view) {
        Intent intent = new Intent(this, DonateActivity.class);
        startActivity(intent);
    }

    public void userSignOut(View view) {
        FirebaseAuth.getInstance().signOut();

        Intent intent = new Intent(this, MainFirebaseActivity.class);
        startActivity(intent);
        MenuLoggedFirebaseActivity.this.finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        super.onBackPressed();
    }

    public void showLastDonation(View view) {
        Intent intent = new Intent(MenuLoggedFirebaseActivity.this, LastDonationActivity.class);
        startActivity(intent);
    }
}