package com.mercacortex.ad_ficheros;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.mercacortex.ad_ficheros.R;
import com.mercacortex.ad_ficheros.adapter.RecyclerAdapter;

public class AgendaActivity extends AppCompatActivity {

    Button btnAdd;
    RecyclerAdapter adapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);
        adapter = new RecyclerAdapter();
        recyclerView = (RecyclerView) findViewById(R.id.agendaRVW);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Lee desde archivo y carga los datos en el adapter
        recyclerView.setAdapter(adapter);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AgendaActivity.this, AddContactActivity.class));
            }
        });
    }

}
