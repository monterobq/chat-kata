package org.ejmc.android.simplechat;

import  org.ejmc.android.simplechat.model.*;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: bqnieves
 * Date: 22/11/13
 * Time: 10:21
 * To change this template use File | Settings | File Templates.
 */
public class CustomAdapter extends BaseAdapter {

    private ArrayList<Message> listadoMensajes;
    private LayoutInflater lInflater;

    public CustomAdapter(Context context, ArrayList<Message> mensajes) {
        this.lInflater = LayoutInflater.from(context);
        this.listadoMensajes = mensajes;
    }

    @Override
    public int getCount() { return listadoMensajes.size(); }

    @Override
    public Object getItem(int arg0) { return listadoMensajes.get(arg0); }

    @Override
    public long getItemId(int arg0) { return arg0; }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {
        ContenedorView contenedor = null;

        if (arg1 == null){
            arg1 = lInflater.inflate(R.layout.chat_item, null);

            contenedor = new ContenedorView();
            contenedor.nombre = (TextView) arg1.findViewById(R.id.nick);
            contenedor.mensaje = (TextView) arg1.findViewById(R.id.mensaje);
            contenedor.logo = (ImageView) arg1.findViewById(R.id.logo);


            arg1.setTag(contenedor);
        } else
            contenedor = (ContenedorView) arg1.getTag();

        Message versiones = (Message) getItem(arg0);
        contenedor.nombre.setText(versiones.getNombre());
        contenedor.mensaje.setText(versiones.getMensaje());
        contenedor.logo.setImageResource(versiones.getScr());

        return arg1;
    }

    class ContenedorView{
        TextView nombre;
        TextView mensaje;
        ImageView logo;
    }

}