package com.example.donacionesuabc;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
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

    public Uri imguri;
    StorageReference mStorageRef;
    StorageTask uploadTask;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);

        mStorageRef = FirebaseStorage.getInstance().getReference("Images");

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

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        Toast.makeText(DonateActivity.this,  user.getDisplayName(), Toast.LENGTH_LONG).show();
        Toast.makeText(DonateActivity.this, user.getEmail(), Toast.LENGTH_LONG).show();
    }

    public void uploadDonation(View view) {
        if (uploadTask != null && uploadTask.isInProgress()) {
            Toast.makeText(DonateActivity.this, "La publicación está en progreso", Toast.LENGTH_LONG).show();
        } else {
            fileUploader();
        }
    }

    // En fileChooser() basicamente lo unico que estamos haciendo es desplegar en el ImageView la imagen seleccionada por nosotros
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

    private void fileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Elige la imagen de tu artículo"), PICK_IMAGE_REQUEST);

    }

    private String getExtension(Uri uri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }

    private void fileUploader() {
        StorageReference ref = mStorageRef.child(System.currentTimeMillis()+"."+getExtension(imguri));

        uploadTask = ref.putFile(imguri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        Toast.makeText(DonateActivity.this, "La Donacion ha sido subida exitosamente", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                    }
                });
    }

}
