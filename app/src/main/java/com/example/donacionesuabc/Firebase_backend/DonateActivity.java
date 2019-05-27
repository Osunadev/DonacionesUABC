/* Estuve intentando muchas veces hacer el upload correcto de esto, y finalmente lo pude lograr gracias a este video https://www.youtube.com/watch?v=lPfQN-Sfnjw
    En combinacion de esta respuesta: https://stackoverflow.com/questions/50947932/cant-get-downloadurl-from-firebase
*/
package com.example.donacionesuabc.Firebase_backend;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.donacionesuabc.Firebase_backend.Model.Donacion;
import com.example.donacionesuabc.Firebase_backend.Model.ManagePublicationName;
import com.example.donacionesuabc.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

public class DonateActivity extends AppCompatActivity {


    private final int PICK_IMAGE_REQUEST = 71;
    EditText articleName;
    EditText facultyName;
    EditText category;
    EditText description;
    EditText email;
    EditText facebook;
    EditText celular;
    EditText location;
    Button postBtn;
    Button chooseImgBtn;
    ImageView imgUpload;
    ProgressBar uploadProgress;

    Uri imguri;
    // Firebase Cloud Storage
    StorageReference mStorageRef;
    StorageTask uploadTask;
    // Firebase auth
    FirebaseUser user;
    // Firebase realtime database
    DatabaseReference  myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);

        articleName = findViewById(R.id.articleNameTxt);
        facultyName = findViewById(R.id.facultyTxt);
        category = findViewById(R.id.categoryTxt);
        description = findViewById(R.id.descriptionTxt);
        email = findViewById(R.id.emailTxt);
        facebook = findViewById(R.id.facebookTxt);
        celular = findViewById(R.id.celularTxt);
        location = findViewById(R.id.locationTxt);
        postBtn = findViewById(R.id.postDonationBtn);
        chooseImgBtn = findViewById(R.id.chooseImageBtn);
        imgUpload = findViewById(R.id.uploadImage);
        uploadProgress = findViewById(R.id.progressBar);

        // Cloud Storage
        mStorageRef = FirebaseStorage.getInstance().getReference("Images");

        // Firebase auth
        user = FirebaseAuth.getInstance().getCurrentUser();

        // Database
        myRef = FirebaseDatabase.getInstance().getReference("Donaciones");

        // Prueba
    /*    FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference myRef = db.getReference("mensajes");

        String donacion_id = myRef.push().getKey();
        myRef.child(donacion_id).setValue(System.currentTimeMillis()+"Hola");*/

    }
    /*
        Metodos que involucran subir la informacion de la donacion a Firebase:

        - Primero se sube la imagen del articulo a donar a nuestro Firebase Cloud Storage
        - Segundo se recupera el url de la imagen ya subida a Firebase y despues se sube el
          url de la imagen, junto con la informaciond de la publicacion hacia nuestro Firebase
          Realtime Database

     */
    public void makeDonation(View view) {
        if (!articleName.getText().toString().isEmpty() && !facultyName.getText().toString().isEmpty()
                && !category.getText().toString().isEmpty() && !description.getText().toString().isEmpty()
                && !email.getText().toString().isEmpty() && !facebook.getText().toString().isEmpty()
                && !celular.getText().toString().isEmpty() && !location.getText().toString().isEmpty() ) {

            if (uploadTask != null && uploadTask.isInProgress()) {
                Toast.makeText(DonateActivity.this, "Donacion en progreso.", Toast.LENGTH_SHORT).show();
            } else {
                uploadDonation();
            }
        } else {
            Toast.makeText(DonateActivity.this, "Llene todos los campos, por favor.", Toast.LENGTH_LONG).show();
        }
    }

    private void uploadDonation() {

        final String donation_id = myRef.push().getKey();                                                                   // key que tendra nuestra publicacion de donacion
        String imageName = ManagePublicationName.imageFormatName(user.getUid(), articleName.getText().toString());          // nombre que trendra nuestra imagen al ser alojada en Firebase

        // Subiendo imagen a Firebase Cloud Storage
        final StorageReference ref = mStorageRef.child(imageName);
        uploadTask = ref.putFile(imguri)
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                        uploadProgress.setProgress((int) progress);
                    }
                });

        uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return ref.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {

            // Aqui vamos a recuperar el url de la imagen subida a Firebase y vamos a guardar la informacion de la donacion
            // en nuestra Realtime Database
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            uploadProgress.setProgress(0);
                            clearDonationFields();
                        }
                    }, 500);
                    Toast.makeText(DonateActivity.this, "Publicacion para donacion exitosa.", Toast.LENGTH_SHORT).show();
                    // Se guarda informacion de la donacion a Firebase Database
                    Donacion donacion = new Donacion(user.getUid(), articleName.getText().toString(), facultyName.getText().toString(), category.getText().toString(),
                            description.getText().toString(), email.getText().toString(), facebook.getText().toString(), celular.getText().toString(),
                            location.getText().toString(), task.getResult().toString());

                    myRef.child(donation_id).setValue(donacion);

                } else {
                    Toast.makeText(DonateActivity.this, "ERROR. No se ha podido publicar la donacion", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void clearDonationFields() {
        articleName.setText("");
        facultyName.setText("");
        category.setText("");
        description.setText("");
        email.setText("");
        facebook.setText("");
        celular.setText("");
        location.setText("");
        imgUpload.setImageBitmap(null);
    }

    /* Metodos que involucran elegir la imagen desde nuestro celular */
    public void chooseImage(View view) {
        fileChooser();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data!=null && data.getData()!= null) {
            imguri = data.getData();
            imgUpload.setImageURI(imguri);
        }
    }

    // En fileChooser() basicamente lo unico que estamos haciendo es desplegar en el ImageView la imagen seleccionada por nosotros
    private void fileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Elige la imagen de tu artículo"), PICK_IMAGE_REQUEST);

    }
}
