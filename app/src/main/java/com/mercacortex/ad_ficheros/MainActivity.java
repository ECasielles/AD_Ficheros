package com.mercacortex.ad_ficheros;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.mercacortex.ad_ficheros.R;
import com.mercacortex.ad_ficheros.activity.AgendaActivity;
import com.mercacortex.ad_ficheros.activity.AlarmaActivity;
import com.mercacortex.ad_ficheros.activity.ConexionActivity;
import com.mercacortex.ad_ficheros.activity.PeriodoActivity;

public class MainActivity extends AppCompatActivity {

    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn1 = (Button) findViewById(R.id.mainBtn1);
        btn2 = (Button) findViewById(R.id.mainBtn2);
        btn3 = (Button) findViewById(R.id.mainBtn3);
        btn4 = (Button) findViewById(R.id.mainBtn4);
        btn5 = (Button) findViewById(R.id.mainBtn5);
        btn6 = (Button) findViewById(R.id.mainBtn6);
        btn7 = (Button) findViewById(R.id.mainBtn7);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AgendaActivity.class);
                startActivity(intent);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AlarmaActivity.class);
                startActivity(intent);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PeriodoActivity.class);
                startActivity(intent);
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ConexionActivity.class);
                startActivity(intent);
            }
        });
    }
}
