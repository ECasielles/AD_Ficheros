package com.mercacortex.ad_ficheros.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import com.mercacortex.ad_ficheros.Memoria;
import com.mercacortex.ad_ficheros.R;
import com.mercacortex.ad_ficheros.Resultado;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class PeriodoActivity extends AppCompatActivity {

    Button btnFecha, btnCalcular, btnLimpiar;
    CalendarView calendarView;
    Calendar calendar;
    Memoria memoria;
    String ruta = "calendario.txt";
    ArrayList<String> fechas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_periodo);
        initialize();

        calendarView = (CalendarView) findViewById(R.id.cvPeriodo);
        btnFecha = (Button) findViewById(R.id.btnFecha);
        btnCalcular = (Button) findViewById(R.id.btnPredict);
        btnLimpiar = (Button) findViewById(R.id.btnLimpiar);

        calendar = Calendar.getInstance();
        calendarView.setFirstDayOfWeek(Calendar.MONDAY);
        calendarView.setDate(calendar.getTimeInMillis());
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                calendar.set(year, month, day);
            }
        });

        btnLimpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PeriodoActivity.this, "Creada nueva lista de fechas", Toast.LENGTH_SHORT).show();
                memoria.escribirInterna(ruta, "", false, "UTF-8");
                fechas.clear();
            }
        });
        btnFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nuevaFecha = String.valueOf(calendar.get(Calendar.YEAR)) + "/" +
                        String.valueOf(calendar.get(Calendar.MONTH)) + "/" +
                        String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
                memoria.escribirInterna(ruta, nuevaFecha + "\n", true, "UTF-8");
                fechas.add(nuevaFecha);
                Toast.makeText(PeriodoActivity.this, "Agregada nueva fecha", Toast.LENGTH_SHORT).show();
            }
        });
        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fechas.size() < 2)
                    Toast.makeText(PeriodoActivity.this, "No hay suficientes fechas para calcular: " + fechas.size() + " < 2.", Toast.LENGTH_SHORT).show();
                else
                    calcular();
            }
        });
    }
    private void calcular() {
        try {
            long prediccionMax = 0L;
            long prediccionMin = Long.MAX_VALUE;
            long aux = 0L;

            if(fechas.size() > 1) {
                for (int i = 0; i < fechas.size(); i++) {
                    String[] fecha = fechas.get(i).split("/");
                    calendar.set(
                            Integer.valueOf(fecha[0]),
                            Integer.valueOf(fecha[1]),
                            Integer.valueOf(fecha[2])
                    );
                    aux = calendar.getTimeInMillis();
                    if(aux > prediccionMax)
                        prediccionMax = aux;
                    if (aux < prediccionMin)
                        prediccionMin = aux;
                }
                aux = (prediccionMax - prediccionMin) / (fechas.size() - 1) + prediccionMax;
                calendarView.setDate(aux);
                calendar.setTimeInMillis(aux);
                String nuevaFecha = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH) + "/" +
                                String.valueOf(calendar.get(Calendar.MONTH) + 1) + "/" +
                                String.valueOf(calendar.get(Calendar.YEAR))
                        );
                Toast.makeText(PeriodoActivity.this, "Fecha estimada del próximo período: " + nuevaFecha, Toast.LENGTH_LONG).show();

            }
        } catch (Exception e) {
            Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        }
    }
    private void initialize() {
        try {
            Resultado lectura;
            memoria = new Memoria(this);
            lectura = memoria.leerInterna(ruta, "UTF-8");
            fechas = new ArrayList<>();
            if (lectura.getCodigo()) {
                fechas.addAll(Arrays.asList(lectura.getContenido().split("\n")));
                Toast.makeText(this, "Leída lista de fechas desde archivo", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Creada nueva lista de fechas", Toast.LENGTH_SHORT).show();
                memoria.escribirInterna(ruta, "", false, "UTF-8");
            }
        } catch (Exception e) {
            Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        }
    }

}
