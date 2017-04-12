package com.mercacortex.ad_ficheros;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ContactViewHolder>{

    private final Context context;
    private ArrayList<Contact> contacts;

    public RecyclerAdapter(Context context, Resultado lectura){
        this.context = context;
        String[] lineas = lectura.getContenido().split("\n");
        contacts = new ArrayList<>();
        for(String linea: lineas) {
            String[] dato = linea.split(";");
            contacts.add(new Contact(dato[0], dato[1], dato[2]));
        }
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        return new ContactViewHolder(item, R.id.txvNombre, R.id.txvTelefono, R.id.txvEmail);
    }
    @Override
    public void onBindViewHolder (ContactViewHolder  holder, int position){
        holder.txvNombre.setText(contacts.get(position).getNombre());
        holder.txvTelefono.setText(contacts.get(position).getTelefono());
        holder.txvEmail.setText(contacts.get(position).getEmail());
    }
    @Override
    public int getItemCount() {
        return contacts.size();
    }
    public boolean addContact(Contact contact){
        boolean result = contacts.add(contact);
        notifyDataSetChanged();
        return result;
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder{
        TextView txvNombre, txvTelefono, txvEmail;

        public ContactViewHolder(View itemView, int txvNombre, int txvTelefono, int txvEmail) {
            super(itemView);
            this.txvNombre = (TextView) itemView.findViewById(txvNombre);
            this.txvTelefono = (TextView) itemView.findViewById(txvTelefono);
            this.txvEmail = (TextView) itemView.findViewById(txvEmail);
        }
    }

}