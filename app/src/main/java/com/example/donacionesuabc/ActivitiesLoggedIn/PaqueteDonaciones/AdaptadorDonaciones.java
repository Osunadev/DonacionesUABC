package com.example.donacionesuabc.ActivitiesLoggedIn.PaqueteDonaciones;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.donacionesuabc.Articulo;
import com.example.donacionesuabc.R;

import java.util.ArrayList;

public class AdaptadorDonaciones extends BaseAdapter {
    private Context context;
    private ArrayList<Articulo> listItems;

    public AdaptadorDonaciones(Context context, ArrayList<Articulo> listItems){
        this.context = context;
        this.listItems = listItems;
    }


    @Override
    public int getCount() {
        return listItems.size();
    }

    @Override
    public Object getItem(int position) {
        return listItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Articulo Item = (Articulo) getItem(position);

        /**
         * Funciona igual que adaptador articulos pero con un componente adicional
         */

        convertView = LayoutInflater.from(context).inflate(R.layout.formato_lista_donaciones,null);
        ImageView imgFoto = (ImageView) convertView.findViewById(R.id.imgFotoDonacion);
        TextView tvTitulo = (TextView) convertView.findViewById(R.id.tvNombreDonacion);
        TextView tvDescripcion = (TextView) convertView.findViewById(R.id.tvDescripcionDonacion);

        imgFoto.setClickable(true);
        imgFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i  = new Intent(context, EstadoDonacion.class);
                i.putExtra("articulo",Item);
                context.startActivity(i);
            }
        });

        imgFoto.setImageResource(Item.getImgFoto());
        tvTitulo.setText(Item.getTitulo());
        tvDescripcion.setText(Item.getDescripcion());

        return convertView;
    }
}
