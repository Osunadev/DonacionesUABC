package com.example.donacionesuabc.ActivitiesLoggedIn.PaqueteDonaciones;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.donacionesuabc.ActivitiesLoggedIn.MenuLoggedActivity;
import com.example.donacionesuabc.CustomAdapter;
import com.example.donacionesuabc.CustomItems;
import com.example.donacionesuabc.MainActivity;
import com.example.donacionesuabc.Model.Donacion;
import com.example.donacionesuabc.Model.ManagePublicationName;
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

import java.util.ArrayList;

public class SubirDonacion extends AppCompatActivity {

    private static final int PICK_IMAGE = 100;
    private Spinner spinnerFacultad;
    private Spinner spinnerCategoria;
    private ImageView item_img;
    private ImageButton back;
    private ImageButton home;

    Button postBtn;
    EditText articleName;
    EditText facultyName;
    EditText category;
    EditText description;
    EditText email;
    EditText facebook;
    EditText celular;
    ProgressBar uploadProgress;

    private String facultad;
    private String categoria;

    Uri imguri;
    // Firebase Cloud Storage
    StorageReference mStorageRef;
    StorageTask uploadTask;
    // Firebase auth
    FirebaseUser user;
    // Firebase realtime database
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subir_donacion);

        item_img = (ImageView) findViewById(R.id.modFotoArticulo);
        back = (ImageButton) findViewById(R.id.backBtn2);
        home = (ImageButton) findViewById(R.id.homeBtn2);

        articleName = findViewById(R.id.subTxtBoxNombre);
        description = findViewById(R.id.subTxtBoxDescripcion);
        email = findViewById(R.id.subTxtBoxEmail);
        facebook = findViewById(R.id.subTxtBoxFacebook);
        celular = findViewById(R.id.subTxtBoxTelefono);
        postBtn = findViewById(R.id.btnSubir);
        item_img = findViewById(R.id.subFotoArticulo);
        uploadProgress = findViewById(R.id.progressBarDonacion);

        /**
         * Se inicializan los spinners
         */
        spinnerFacultad = findViewById(R.id.subSpinnerFacultad);
        spinnerCategoria = findViewById(R.id.subSpinnerCategoria);
        //Crear la lista del spinner de facultad
        ArrayList<CustomItems> facultades=new ArrayList<>();
        facultades.add(new CustomItems("Seleccione Facultad",R.drawable.facultades));
        facultades.add(new CustomItems("Artes",R.drawable.artes));
        facultades.add(new CustomItems("Ciencias Quimicas e Ingenieria",R.drawable.ingenieria));
        facultades.add(new CustomItems("Contaduria y Administracion",R.drawable.administracion));
        facultades.add(new CustomItems("Deportes",R.drawable.deportes));
        facultades.add(new CustomItems("Derecho",R.drawable.derecho));
        facultades.add(new CustomItems("Economia y Relaciones Internacionales",R.drawable.economia));
        facultades.add(new CustomItems("Humanidades y Ciencias Sociales",R.drawable.humanidades));
        facultades.add(new CustomItems("Idiomas",R.drawable.idiomas));
        facultades.add(new CustomItems("Medicina y Psicologia",R.drawable.medicina));
        facultades.add(new CustomItems("Odontologia",R.drawable.odontologia));
        facultades.add(new CustomItems("Turismo",R.drawable.turismo));
        facultades.add(new CustomItems("Investigaciones Historicas",R.drawable.historia));

        //Crear la lista del spinner de categorias
        ArrayList<CustomItems> categorias=new ArrayList<>();
        categorias.add(new CustomItems("Seleccione Categoria",R.drawable.categorias));
        categorias.add(new CustomItems("Libros",R.drawable.libros));
        categorias.add(new CustomItems("Ropa",R.drawable.ropa));
        categorias.add(new CustomItems("Dispositivos Electronicos",R.drawable.electronicos));
        categorias.add(new CustomItems("Utencilios de Artes",R.drawable.utencilios_artes));
        categorias.add(new CustomItems("Instrumentos Musicales",R.drawable.instrumentos_musicales));
        categorias.add(new CustomItems("Materiales de Quimica",R.drawable.materiales_quimica));
        categorias.add(new CustomItems("Articulos Deportivos",R.drawable.deportivos));
        categorias.add(new CustomItems("Articulos Medicos",R.drawable.articulos_medicos));

        //Hacer el adaptador de los spinners
        CustomAdapter customAdapter=new CustomAdapter(this,facultades);
        CustomAdapter customAdapter2=new CustomAdapter(this,categorias);

        if(spinnerFacultad!=null){
            spinnerFacultad.setAdapter(customAdapter);
            spinnerFacultad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    CustomItems items=(CustomItems) parent.getSelectedItem();
                    facultad = items.getSpinnerText();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
        if(spinnerCategoria!=null){
            spinnerCategoria.setAdapter(customAdapter2);
            spinnerCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    CustomItems items=(CustomItems) parent.getSelectedItem();
                    categoria = items.getSpinnerText();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }

        // Cloud Storage
        mStorageRef = FirebaseStorage.getInstance().getReference("Images");

        // Firebase auth
        user = FirebaseAuth.getInstance().getCurrentUser();

        // Database
        myRef = FirebaseDatabase.getInstance().getReference("Donaciones");

    }

    // Metodos Responsables de elegir la foto desde el celular y mostrarla en el ImageView
    private void pickup_photo(){
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(i,PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        imguri = data.getData();

        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            item_img.setImageURI(imguri);
        }

    }

    // Listeners de los botones
    public void backHome(View view) {
        Intent intent = new Intent(getApplicationContext(), MenuLoggedActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    public void backArrow(View view) {
        finish();
    }

    public void selectPhoto(View view) {
        pickup_photo();
    }


    /*
    Metodos que involucran subir la informacion de la donacion a Firebase:

    - Primero se sube la imagen del articulo a donar a nuestro Firebase Cloud Storage
    - Segundo se recupera el url de la imagen ya subida a Firebase y despues se sube el
      url de la imagen, junto con la informaciond de la publicacion hacia nuestro Firebase
      Realtime Database

 */
    public void makeDonation(View view) {
        if (!articleName.getText().toString().isEmpty() && !facultad.isEmpty() && !categoria.isEmpty()
                && !description.getText().toString().isEmpty() && !email.getText().toString().isEmpty()
                && !facebook.getText().toString().isEmpty() && !celular.getText().toString().isEmpty()) {

            if (uploadTask != null && uploadTask.isInProgress()) {
                Toast.makeText(SubirDonacion.this, "Donacion en progreso.", Toast.LENGTH_SHORT).show();
            } else {
                uploadDonation();
            }
        } else {
            Toast.makeText(SubirDonacion.this, "Llene todos los campos, por favor.", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(SubirDonacion.this, "Publicacion para donacion exitosa.", Toast.LENGTH_SHORT).show();
                    // Se guarda informacion de la donacion a Firebase Database
                    Donacion donacion = new Donacion(user.getUid(), articleName.getText().toString(), facultad, categoria, description.getText().toString(),
                            user.getEmail(), email.getText().toString(), facebook.getText().toString(), celular.getText().toString(), task.getResult().toString());

                    myRef.child(donation_id).setValue(donacion);

                } else {
                    Toast.makeText(SubirDonacion.this, "ERROR. No se ha podido publicar la donacion", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void clearDonationFields() {
        articleName.setText("");
        description.setText("");
        email.setText("");
        facebook.setText("");
        celular.setText("");
        item_img.setImageDrawable(null);
    }

}
