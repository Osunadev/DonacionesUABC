package com.example.donacionesuabc.Firebase_backend;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.donacionesuabc.Firebase_backend.Model.Donacion;
import com.example.donacionesuabc.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class LastDonationActivity extends AppCompatActivity {

    EditText lastArticleName;
    EditText lastFacultyName;
    EditText lastCategory;
    EditText lastDescription;
    EditText lastEmail;
    EditText lastFacebook;
    EditText lastCelular;
    EditText lastLocation;
    ImageView lastImgUpload;
    ProgressBar lastUploadProgress;

    // Firebase auth
    FirebaseUser user;
    // Firebase realtime database
    DatabaseReference donacionesRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_donation);

        lastArticleName = findViewById(R.id.lastArticleNameTxt);
        lastFacultyName = findViewById(R.id.lastFacultyTxt);
        lastCategory = findViewById(R.id.lastCategoryTxt);
        lastDescription = findViewById(R.id.lastDescriptionTxt);
        lastEmail = findViewById(R.id.lastEmailTxt);
        lastFacebook = findViewById(R.id.lastFacebookTxt);
        lastCelular = findViewById(R.id.lastCelularTxt);
        lastLocation = findViewById(R.id.lastLocationTxt);
        lastImgUpload = findViewById(R.id.lastUploadImage);
        lastUploadProgress = findViewById(R.id.lastProgressBar);

        // Firebase auth
        user = FirebaseAuth.getInstance().getCurrentUser();

        // Firebase Realtime Database
        donacionesRef = FirebaseDatabase.getInstance().getReference("Donaciones");

        donacionesRef.orderByKey().limitToLast(1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Este post de stack overflow me ayudo a debbugear mi codigo porque como la clase Donacion no tenia un constructor vacio, entonces me marcaba error
                // porque DataSnapshot.getValue(Class<T> valueType) debe recibir una clase con un constructor vacio:
                // https://stackoverflow.com/questions/37830692/parsing-from-datasnapshot-to-java-class-in-firebase-using-getvalue

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Donacion articuloDonacion = data.getValue(Donacion.class);
                    fillDonationFields(articuloDonacion);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(LastDonationActivity.this, "Failed to read value.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fillDonationFields(Donacion publicacionDonacion) {
        lastArticleName.setText(publicacionDonacion.getArticleName());
        lastFacultyName.setText(publicacionDonacion.getFacultyName());
        lastCategory.setText(publicacionDonacion.getCategory());
        lastDescription.setText(publicacionDonacion.getDescription());
        lastEmail.setText(publicacionDonacion.getEmail());
        lastFacebook.setText(publicacionDonacion.getFacebook());
        lastCelular.setText(publicacionDonacion.getCelular());
        lastLocation.setText(publicacionDonacion.getLocation());

        Picasso.get().load(publicacionDonacion.getImageUrl()).into(lastImgUpload);

    }

}
