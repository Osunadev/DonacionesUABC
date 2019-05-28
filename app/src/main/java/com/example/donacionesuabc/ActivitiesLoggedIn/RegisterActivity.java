package com.example.donacionesuabc.ActivitiesLoggedIn;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.donacionesuabc.CustomAdapter;
import com.example.donacionesuabc.CustomItems;
import com.example.donacionesuabc.Model.User;
import com.example.donacionesuabc.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private TextView nombre;
    private TextView apellidos;
    private TextView fechaNacimiento;
    private Spinner spinnerFacultad;
    private TextView correo;
    private TextView matricula;
    private TextView password;
    private Button btnRegistro;

    private String facultad;
    // Firebase auth
    private FirebaseAuth mAuth;
    // Firebase realtime database
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nombre = (TextView) findViewById(R.id.txtNombre);
        apellidos = (TextView) findViewById(R.id.txtApellidos);
        fechaNacimiento = (TextView) findViewById(R.id.fechaNacimiento);
        correo = (TextView) findViewById(R.id.txtCorreo);
        matricula = (TextView) findViewById(R.id.txtmatricula);
        password = (TextView) findViewById(R.id.passwordRegistro);
        btnRegistro = (Button) findViewById(R.id.btnRegistrar);
        spinnerFacultad = (Spinner) findViewById(R.id.RegistroSpinnerFacultad);

        //Crear la lista del spinner de facultad
        ArrayList<CustomItems> facultades = new ArrayList<>();
        facultades.add(new CustomItems("Seleccione Facultad", R.drawable.facultades));
        facultades.add(new CustomItems("Artes", R.drawable.artes));
        facultades.add(new CustomItems("Ciencias Quimicas e Ingenieria", R.drawable.ingenieria));
        facultades.add(new CustomItems("Contaduria y Administracion", R.drawable.administracion));
        facultades.add(new CustomItems("Deportes", R.drawable.deportes));
        facultades.add(new CustomItems("Derecho", R.drawable.derecho));
        facultades.add(new CustomItems("Economia y Relaciones Internacionales", R.drawable.economia));
        facultades.add(new CustomItems("Humanidades y Ciencias Sociales", R.drawable.humanidades));
        facultades.add(new CustomItems("Idiomas", R.drawable.idiomas));
        facultades.add(new CustomItems("Medicina y Psicologia", R.drawable.medicina));
        facultades.add(new CustomItems("Odontologia", R.drawable.odontologia));
        facultades.add(new CustomItems("Turismo", R.drawable.turismo));
        facultades.add(new CustomItems("Investigaciones Historicas", R.drawable.historia));

        //Hacer el adaptador de los spinners
        CustomAdapter customAdapter = new CustomAdapter(this, facultades);
        if (spinnerFacultad != null) {
            spinnerFacultad.setAdapter(customAdapter);
            spinnerFacultad.setOnItemSelectedListener(this);
        }

        mAuth = FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference("Usuarios");
    }

    public void registroBackArrow(View view) {
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        CustomItems items = (CustomItems) adapterView.getSelectedItem();
        facultad = items.getSpinnerText();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void registerUserFirebase(View view) {
        if (!nombre.getText().toString().isEmpty() && !apellidos.getText().toString().isEmpty()
        && !fechaNacimiento.getText().toString().isEmpty() && !correo.getText().toString().isEmpty()
        && !matricula.getText().toString().isEmpty() && !password.getText().toString().isEmpty()
        && !facultad.isEmpty()) {
            if (password.getText().toString().length() < 8) {
                Toast.makeText(this, "Introduzca una contraseña mayor a 8 carácteres.", Toast.LENGTH_SHORT).show();
            } else if (matricula.getText().toString().length() != 7) {
                Toast.makeText(this, "Introduzca una matrícula válida.", Toast.LENGTH_SHORT).show();
            } else {
                btnRegistro.setEnabled(false);
                register(correo.getText().toString(), password.getText().toString());
            }
        } else {
            Toast.makeText(RegisterActivity.this, "Por favor llene todos los campos.", Toast.LENGTH_SHORT).show();
        }
    }

    private void register(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            uploadUserInforFirebase();
                            Toast.makeText(RegisterActivity.this, "¡Registro exitoso!", Toast.LENGTH_SHORT).show();
                        } else {
                            btnRegistro.setEnabled(true);
                            Toast.makeText(RegisterActivity.this, "Autenticación Fallida, inténtelo de nuevo.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void uploadUserInforFirebase() {
        FirebaseUser fireUser = FirebaseAuth.getInstance().getCurrentUser();

        String user_publication_id = myRef.push().getKey();
        // Handler para llevar un tracking de si ya se realizo la creacion de usuario y si ya se subio la informacion
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                changeToMenuLoggedIn();
            }
        }, 500);

        User user = new User(nombre.getText().toString(), apellidos.getText().toString(), fechaNacimiento.getText().toString()
                        , facultad, fireUser.getEmail(), fireUser.getUid(), matricula.getText().toString());

        myRef.child(user_publication_id).setValue(user);
    }

    private void changeToMenuLoggedIn() {
        startActivity(new Intent(this, MenuLoggedActivity.class));
    }
}