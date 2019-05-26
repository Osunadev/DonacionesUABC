package com.example.donacionesuabc.ActivitiesLoggedIn;

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
import com.example.donacionesuabc.VistaArticulo;

import java.util.ArrayList;

public class AdaptadorArticulos extends BaseAdapter {
    private Context context;
    private ArrayList<Articulo> listItems;

    public AdaptadorArticulos(Context context, ArrayList<Articulo> listItems){
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
        Este adaptador convierte los articulos recientes a xml
         */
        convertView = LayoutInflater.from(context).inflate(R.layout.formato_lista_articulos,null);
        ImageView imgFoto = (ImageView) convertView.findViewById(R.id.imgFoto);
        TextView tvTitulo = (TextView) convertView.findViewById(R.id.tvTitulo);

        /**
         Aqui se les da funcionalidad de boton para ver sus detalles
         */
        imgFoto.setClickable(true);
        imgFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i  = new Intent(context, VistaArticulo.class);
                i.putExtra("articulo",Item);
                context.startActivity(i);
            }
        });

        /**
         * Escribe la informacion del articulo en los componentes
         */
        imgFoto.setImageResource(Item.getImgFoto());
        tvTitulo.setText(Item.getTitulo());

        return convertView;
    }
}
