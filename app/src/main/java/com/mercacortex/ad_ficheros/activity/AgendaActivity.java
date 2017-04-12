package com.mercacortex.ad_ficheros.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mercacortex.ad_ficheros.Contact;
import com.mercacortex.ad_ficheros.Memoria;
import com.mercacortex.ad_ficheros.R;
import com.mercacortex.ad_ficheros.RecyclerAdapter;

public class AgendaActivity extends AppCompatActivity {

    String ruta = "contactos.txt";
    Button btnAdd;
    RecyclerAdapter adapter;
    RecyclerView recyclerView;
    Memoria memoria;
    static final int NEW_CONTACT = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(AgendaActivity.this, AddContactActivity.class), 1);
            }
        });

        try {
            memoria = new Memoria(this);

            //Garantizamos que haya al menos un archivo de contactos
            //creando un archivo por defecto
            initialize(memoria);
            adapter = new RecyclerAdapter(this, memoria.leerInterna(ruta, "UTF-8"));

            recyclerView = (RecyclerView) findViewById(R.id.rvwAgenda);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            //Lee desde archivo y carga los datos en el adapter
            recyclerView.setAdapter(adapter);
        } catch (Exception e) {
            Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        }
    }

    //Crea un archivo con valores por defecto para pruebas
    private void initialize(Memoria memoria) {
        if (!memoria.leerInterna(ruta, "UTF-8").getCodigo()) {
            Toast.makeText(this, "Creando agenda nueva", Toast.LENGTH_SHORT).show();

            String[] nombres = {"Jose", "Pepa", "Juan", "Juana"};
            String[] correos = {"@hotmail.com", "@gmail.com", "@yahoo.com", "@terra.es"};
            int telefono = 80099001;

            for (String nombre: nombres)
                for (String correo: correos)
                    memoria.escribirInterna(ruta, (new Contact(nombre, String.valueOf(telefono++), nombre + correo)).toString() + "\n", true, "UTF-8");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case RESULT_OK:
                Contact nuevoContacto = (Contact) data.getSerializableExtra("contacto");
                adapter.addContact(nuevoContacto);
                memoria.escribirInterna(ruta, nuevoContacto.toString() + "\n", true, "UTF-8");
                Toast.makeText(this, "Añadido un nuevo contacto", Toast.LENGTH_SHORT).show();
                break;
            case RESULT_CANCELED:
                Toast.makeText(this, "No se añadió contacto", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}


