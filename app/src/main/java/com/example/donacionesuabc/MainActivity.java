package com.example.donacionesuabc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.donacionesuabc.ActivitiesLoggedIn.MenuLoggedActivity;

public class MainActivity extends AppCompatActivity {

    Button btnArticulos;
    Button logInButton;
    TextView registroTxt;
    TextView titleApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnArticulos = (Button) findViewById(R.id.verArticulosBtn);
        logInButton = (Button) findViewById(R.id.loginBtn);
        registroTxt = (TextView) findViewById(R.id.registroTxt);
        titleApp = (TextView) findViewById(R.id.menuTitleTxt);
    }
    public void logInUser(View view) {
        // Por ahorita este boton nos dirigira directamente a la pantalla de menu de usuario Logged In
        Intent i1 = new Intent(this, MenuLoggedActivity.class);
        startActivity(i1);
    }


    /****/
    public void menu(View view){
                /**esta es la linea original*/
            startActivity(new Intent(this, VerArticulos.class));

            /**esta linea es para probar la view 4.1 donaciones*/
            //startActivity(new Intent(this, Donaciones.class));
    }
}
