package com.example.donacionesuabc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class MenuLoggedActivity extends AppCompatActivity {

    Button donacionesBtn;
    Button intercambiosBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_logged);

        donacionesBtn = (Button) findViewById(R.id.donacionesBtn);
        intercambiosBtn = (Button) findViewById(R.id.intercambiosBtn);

    }
}
