package com.mercacortex.ad_ficheros.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mercacortex.ad_ficheros.Contact;
import com.mercacortex.ad_ficheros.R;

public class AddContactActivity extends AppCompatActivity {

    String ruta = "contactos.txt";
    EditText edtNombre, edtTelefono, edtEmail;
    Button btnNuevo;
    static final int NEW_CONTACT = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        edtNombre = (EditText) findViewById(R.id.edtNombre);
        edtTelefono = (EditText) findViewById(R.id.edtTelefono);
        edtEmail = (EditText) findViewById(R.id.edtEmail);

        btnNuevo = (Button) findViewById(R.id.btnOK);

        btnNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!edtNombre.getText().toString().isEmpty() &&
                        !edtTelefono.getText().toString().isEmpty() &&
                        !edtEmail.getText().toString().isEmpty()) {
                    Contact contact = new Contact(
                            edtNombre.getText().toString(),
                            edtTelefono.getText().toString(),
                            edtEmail.getText().toString()
                    );
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("contacto", contact);
                    setResult(RESULT_OK, returnIntent);
                    finish();
                } else {
                    setResult(RESULT_CANCELED, new Intent());
                }
                finish();
            }
        });
    }

}
