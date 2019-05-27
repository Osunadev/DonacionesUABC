package com.example.donacionesuabc.ActivitiesLoggedIn;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.donacionesuabc.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogInActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText email;
    private EditText pass;
    private Button logInButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        mAuth = FirebaseAuth.getInstance();
        email = (EditText) findViewById(R.id.emailLoginTxt);
        pass = (EditText) findViewById(R.id.passwordTxt);
        logInButton = (Button) findViewById(R.id.loginBtn);
    }

    // onClick Listener para el boton
    public void signInUser(View view) {
        firebaseLogIn();
    }

    public void changeMenuLoggedIn() {
        // Por ahorita este boton nos dirigira directamente a la pantalla de menu de usuario Logged In
        Intent intent = new Intent(this, MenuLoggedActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void firebaseLogIn() {
        String correo = email.getText().toString();
        String contra = pass.getText().toString();

        if ( correo.isEmpty() || contra.isEmpty()) {
            Toast.makeText(LogInActivity.this, "Llene todos los campos.",
                    Toast.LENGTH_SHORT).show();
        } else {
            mAuth.signInWithEmailAndPassword(correo, contra)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                changeMenuLoggedIn();
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(LogInActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }


}
