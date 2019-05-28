package com.example.donacionesuabc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.donacionesuabc.ActivitiesLoggedIn.MenuLoggedActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class VistaArticulo extends AppCompatActivity {

    private ListView Lview;
    private Articulo articulo;

    ImageView imageView;
    TextView titulo;
    TextView facultad;
    TextView descripcion;
    TextView contactos;
    TextView categoria;
    ProgressBar progressBarArticulo;
    Button btnSendMsg;

    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_articulo);

        user = FirebaseAuth.getInstance().getCurrentUser();

        /**
         * Se toman los datos del articulo que llega de la vista anterior
         * y se escriben en los campos correspondientes
         */
        articulo = (Articulo) getIntent().getParcelableExtra("articulo");

        imageView = (ImageView) findViewById(R.id.modFotoArticulo);
        titulo = (TextView) findViewById(R.id.nombreArticulo);
        facultad = (TextView) findViewById(R.id.facultadArticulo);
        descripcion = (TextView) findViewById(R.id.modTxtBoxDescripcion);
        contactos = (TextView) findViewById(R.id.contactoArticulo);
        categoria = (TextView) findViewById(R.id.categoriaArticulo);
        progressBarArticulo = (ProgressBar) findViewById(R.id.progressBarArticulo);
        btnSendMsg = (Button) findViewById(R.id.btnSendMessage);

        // Si no tiene la sesion iniciada o si el articulo es de la misma persona que lo esta viendo
        if (user == null || contactos.getText().toString() == user.getEmail()) {
            btnSendMsg.setVisibility(View.GONE);
        }


        Picasso.get()
                .load(articulo.getImageUrl())
                .resize(400, 400)
                .centerCrop()
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        progressBarArticulo.setVisibility(View.GONE);
                        fillArticleFields();
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
    }

    private void fillArticleFields() {
        titulo.setText(articulo.getTitulo());
        facultad.setText(articulo.getFacultad());
        descripcion.setText(articulo.getDescripcion());
        contactos.setText(articulo.getUserData());
        categoria.setText(articulo.getCategoria());
    }

    public void goBack(View view){
        finish();
    }

    public void goHome(View view){
        Class intentClass;

        if (user != null)
            intentClass = MenuLoggedActivity.class;
        else
            intentClass = MainActivity.class;

        Intent intent = new Intent(getApplicationContext(), intentClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

}
