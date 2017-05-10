package com.example.elro.nopapers;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Random;

public class NextStep extends AppCompatActivity {

    int suma=0;
    int exa,libre,ta,part,traba,asis,ot;
    TextView porcent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_step);
        final EditText cajaexam = (EditText) findViewById(R.id.cajaexamen);
        final EditText cajalibre = (EditText) findViewById(R.id.cajalibreta);
        final EditText cajahw = (EditText) findViewById(R.id.cajatareas);
        final EditText cajaparticip = (EditText) findViewById(R.id.cajaparticipacion);
        final EditText cajatrabajos = (EditText) findViewById(R.id.cajatrabajos);
        final EditText cajaasist = (EditText) findViewById(R.id.cajaasistencias);
        final EditText cajaother = (EditText) findViewById(R.id.cajaotros);
        porcent = (TextView) findViewById(R.id.porcentaje);
        final CheckBox examen = (CheckBox) findViewById(R.id.examencheck);
        final CheckBox libreta = (CheckBox) findViewById(R.id.libretacheck);
        final CheckBox tarea = (CheckBox) findViewById(R.id.tareascheck);
        final CheckBox participacion = (CheckBox) findViewById(R.id.particheck);
        final CheckBox asistencia = (CheckBox) findViewById(R.id.asistenciascheck);
        final CheckBox trabajos = (CheckBox) findViewById(R.id.trabajoscheck);
        final CheckBox otro = (CheckBox) findViewById(R.id.otroscheck);
        TextView prueba = (TextView)findViewById(R.id.prueba);

        Intent openns = getIntent();
        final String namemateria = openns.getStringExtra("namemateria");
        prueba.setText(namemateria);


        cajaexam.setEnabled(true);
        cajalibre.setEnabled(true);
        cajahw.setEnabled(true);
        cajaparticip.setEnabled(true);
        cajatrabajos.setEnabled(true);
        cajaasist.setEnabled(true);
        cajaother.setEnabled(true);
        final MainActivity activity= new MainActivity();




        if (examen != null) {
            examen.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    String valor1 = cajaexam.getText().toString();
                    if (((CheckBox) v).isChecked()) {

                        cajaexam.setEnabled(false);

                        if (valor1.equals("")) {

                            Toast.makeText(getApplicationContext(), "No ha intruducido ningún valor",
                                    Toast.LENGTH_SHORT)
                                    .show();
                            examen.setChecked(false);
                            cajaexam.setEnabled(true);


                        } else {
                            exa = Integer.parseInt(valor1);
                            suma = suma + exa;

                            if (suma > 100) {

                                Toast.makeText(getApplicationContext(), "El maximo porcentaje debe ser 100",
                                        Toast.LENGTH_SHORT)
                                        .show();
                                suma = suma - exa;
                                exa = 0;
                                examen.setChecked(false);
                                cajaexam.setEnabled(true);


                            } else if (suma == 100) {
                                porcent.setText(String.valueOf(suma));
                                porcent.setTextColor(getResources().getColor(R.color.colorAccent));
                            } else {
                                porcent.setText(String.valueOf(suma));

                            }


                        }


                    } else {
                        cajaexam.setEnabled(true);
                        suma = suma - exa;
                        porcent.setText(String.valueOf(suma));
                        cajaexam.setText("");
                        exa = 0;
                        porcent.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    }

                }
            });
        }

        if (libreta != null) {
            libreta.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    String valor2 = cajalibre.getText().toString();

                    if (((CheckBox) v).isChecked()) {

                        cajalibre.setEnabled(false);


                        if (valor2.equals("")) {
                            Toast.makeText(getApplicationContext(), "No ha intruducido ningún valor",
                                    Toast.LENGTH_SHORT)
                                    .show();
                            libreta.setChecked(false);
                            cajalibre.setEnabled(true);


                        } else {

                            libre = Integer.parseInt(valor2);
                            suma = suma + libre;
                            if (suma > 100) {

                                Toast.makeText(getApplicationContext(), "El maximo porcentaje debe ser 100",
                                        Toast.LENGTH_SHORT)
                                        .show();

                                suma = suma - libre;
                                libre = 0;

                                libreta.setChecked(false);
                                cajalibre.setEnabled(true);
                            } else if (suma == 100) {
                                porcent.setText(String.valueOf(suma));
                                porcent.setTextColor(getResources().getColor(R.color.colorAccent));
                            } else {
                                porcent.setText(String.valueOf(suma));
                            }

                        }


                    } else {

                        cajalibre.setEnabled(true);
                        suma = suma - libre;
                        porcent.setText(String.valueOf(suma));
                        cajalibre.setText("");
                        libre = 0;
                        porcent.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    }
                }
            });
        }

        if (tarea != null) {
            tarea.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    String valor1 = cajahw.getText().toString();

                    if (((CheckBox) v).isChecked()) {

                        cajahw.setEnabled(false);


                        if (valor1.equals("")) {


                            Toast.makeText(getApplicationContext(), "No ha intruducido ningún valor",
                                    Toast.LENGTH_SHORT)
                                    .show();
                            tarea.setChecked(false);
                            cajahw.setEnabled(true);


                        } else {

                            ta = Integer.parseInt(valor1);
                            suma = suma + ta;

                            if (suma > 100) {

                                Toast.makeText(getApplicationContext(), "El maximo porcentaje debe ser 100",
                                        Toast.LENGTH_SHORT)
                                        .show();

                                suma = suma - ta;
                                ta = 0;

                                tarea.setChecked(false);
                                cajahw.setEnabled(true);
                            } else if (suma == 100) {
                                porcent.setText(String.valueOf(suma));
                                porcent.setTextColor(getResources().getColor(R.color.colorAccent));
                            } else {
                                porcent.setText(String.valueOf(suma));
                            }


                        }


                    } else {

                        cajahw.setEnabled(true);
                        suma = suma - ta;
                        porcent.setText(String.valueOf(suma));
                        cajahw.setText("");
                        ta = 0;
                        porcent.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    }
                }
            });
        }

        if (participacion != null) {
            participacion.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    String valor1 = cajaparticip.getText().toString();

                    if (((CheckBox) v).isChecked()) {

                        cajaparticip.setEnabled(false);


                        if (valor1.equals("")) {
                            Toast.makeText(getApplicationContext(), "No ha intruducido ningún valor",
                                    Toast.LENGTH_SHORT)
                                    .show();
                            participacion.setChecked(false);
                            cajaparticip.setEnabled(true);


                        } else {

                            part = Integer.parseInt(valor1);
                            suma = suma + part;

                            if (suma > 100) {

                                Toast.makeText(getApplicationContext(), "El maximo porcentaje debe ser 100",
                                        Toast.LENGTH_SHORT)
                                        .show();

                                suma = suma - part;
                                part = 0;

                                participacion.setChecked(false);
                                cajaparticip.setEnabled(true);
                            } else if (suma == 100) {
                                porcent.setText(String.valueOf(suma));
                                porcent.setTextColor(getResources().getColor(R.color.colorAccent));
                            } else {

                                porcent.setText(String.valueOf(suma));
                            }


                        }


                    } else {

                        cajaparticip.setEnabled(true);
                        suma = suma - part;
                        porcent.setText(String.valueOf(suma));
                        cajaparticip.setText("");
                        part = 0;
                        porcent.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    }
                }
            });
        }

        if (asistencia != null) {
            asistencia.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    String valor1 = cajaasist.getText().toString();
                    if (((CheckBox) v).isChecked()) {


                        cajaasist.setEnabled(false);


                        if (valor1.equals("")) {
                            Toast.makeText(getApplicationContext(), "No ha intruducido ningún valor",
                                    Toast.LENGTH_SHORT)
                                    .show();
                            asistencia.setChecked(false);
                            cajaasist.setEnabled(true);


                        } else {

                            asis = Integer.parseInt(valor1);
                            suma = suma + asis;

                            if (suma > 100) {

                                Toast.makeText(getApplicationContext(), "El maximo porcentaje debe ser 100",
                                        Toast.LENGTH_SHORT)
                                        .show();

                                suma = suma - asis;
                                asis = 0;

                                asistencia.setChecked(false);
                                cajaasist.setEnabled(true);
                            } else if (suma == 100) {
                                porcent.setText(String.valueOf(suma));
                                porcent.setTextColor(getResources().getColor(R.color.colorAccent));
                            } else {
                                porcent.setText(String.valueOf(suma));
                            }


                        }


                    } else {

                        cajaasist.setEnabled(true);
                        suma = suma - asis;
                        porcent.setText(String.valueOf(suma));
                        cajaasist.setText("");
                        asis = 0;
                        porcent.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    }
                }
            });
        }

        if (trabajos != null) {
            trabajos.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    String valor1 = cajatrabajos.getText().toString();
                    if (((CheckBox) v).isChecked()) {
                        cajatrabajos.setEnabled(false);


                        if (valor1.equals("")) {
                            Toast.makeText(getApplicationContext(), "No ha intruducido ningún valor",
                                    Toast.LENGTH_SHORT)
                                    .show();
                            trabajos.setChecked(false);
                            cajatrabajos.setEnabled(true);


                        } else {

                            traba = Integer.parseInt(valor1);
                            suma = suma + traba;

                            if (suma > 100) {

                                Toast.makeText(getApplicationContext(), "El maximo porcentaje debe ser 100",
                                        Toast.LENGTH_SHORT)
                                        .show();

                                suma = suma - traba;
                                traba = 0;

                                trabajos.setChecked(false);
                                cajatrabajos.setEnabled(true);
                            } else if (suma == 100) {
                                porcent.setText(String.valueOf(suma));
                                porcent.setTextColor(getResources().getColor(R.color.colorAccent));
                            } else {
                                porcent.setText(String.valueOf(suma));
                            }

                        }


                    } else {

                        cajatrabajos.setEnabled(true);
                        suma = suma - traba;
                        porcent.setText(String.valueOf(suma));
                        cajatrabajos.setText("");
                        traba = 0;
                        porcent.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    }
                }
            });
        }

        if (otro != null) {
            otro.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    String valor1 = cajaother.getText().toString();
                    if (((CheckBox) v).isChecked()) {
                        cajaother.setEnabled(false);


                        if (valor1.equals("")) {
                            Toast.makeText(getApplicationContext(), "No ha intruducido ningún valor",
                                    Toast.LENGTH_SHORT)
                                    .show();
                            otro.setChecked(false);
                            cajaother.setEnabled(true);


                        } else {

                            ot = Integer.parseInt(valor1);
                            suma = suma + ot;

                            if (suma > 100) {

                                Toast.makeText(getApplicationContext(), "El maximo porcentaje debe ser 100",
                                        Toast.LENGTH_SHORT)
                                        .show();

                                suma = suma - ot;
                                ot = 0;

                                otro.setChecked(false);
                                cajaother.setEnabled(true);
                            } else if (suma == 100) {
                                porcent.setText(String.valueOf(suma));
                                porcent.setTextColor(getResources().getColor(R.color.colorAccent));
                            } else {
                                porcent.setText(String.valueOf(suma));
                            }

                        }


                    } else {

                        cajaother.setEnabled(true);
                        suma = suma - ot;
                        porcent.setText(String.valueOf(suma));
                        cajaother.setText("");
                        ot = 0;
                        porcent.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    }
                }
            });
        }

        this.setTitle("Criterio Evaluativos");
        final Button botonnext= (Button)findViewById(R.id.btnsiguiente);


        if (botonnext != null) {
            botonnext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent openns = getIntent();
                    final String nombregrupo = openns.getStringExtra("nombregrupo");
                    final String nivel = openns.getStringExtra("nivel");
                    final String namemateria = openns.getStringExtra("namemateria");
                    final String nameescuela = openns.getStringExtra("nameescuela");
                    final String creador = openns.getStringExtra("creador");
                    final String claveacc = activity.generarclave(6);




                        Response.Listener<String> responseListener = new Response.Listener<String>() {

                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    boolean success = jsonResponse.getBoolean("success");
                                    if (success) {
                                       // Intent grouponew = new Intent(NextStep.this, grupointerfaz.class);
                                     //   grouponew.putExtra("nombregruponew", nombregrupo);
                                       // grouponew.putExtra("creador2new", creador);
                                       // grouponew.putExtra("claveaccnew", claveacc);
                                       // NextStep.this.startActivity(grouponew);
                                       // finish();
                                        Toast.makeText(NextStep.this, "Grupo Creado", Toast.LENGTH_SHORT).show();



                                    } else {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(NextStep.this);
                                        builder.setMessage("Error al crear grupo")
                                                .setNegativeButton("Reintentar", null)
                                                .create()
                                                .show();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        };

                        if (porcent.equals(101)) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(NextStep.this);
                            builder.setMessage("El porcentaje debe ser maximo 100")
                                    .setNegativeButton("Aceptar", null)
                                    .create()
                                    .show();
                            /*
                        }
                        if (nombregrupo.equals("")) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(NextStep.this);
                            builder.setMessage("Introduzca el nombre del grupo")
                                    .setNegativeButton("Aceptar", null)
                                    .create()
                                    .show();
                        } else if (namemateria.equals("")) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(NextStep.this);
                            builder.setMessage("Introduzca su materia")
                                    .setNegativeButton("Aceptar", null)
                                    .create()
                                    .show();
                        } else if (nameescuela.equals("")) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(NextStep.this);
                            builder.setMessage("Introduzca su escuela")
                                    .setNegativeButton("Aceptar", null)
                                    .create()
                                    .show();
                        } else if (nivel.equals("Escoger Nivel")) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(NextStep.this);
                            builder.setMessage("Introduzca el nivel escolar")
                                    .setNegativeButton("Aceptar", null)
                                    .create()
                                    .show();*/

                        } else {
                            /*
                            String examen;
                            String libreta;
                            String tareas;
                            String participacion;
                            String trabajos;
                            String asistencia;
                            String otros;

                            examen = cajaexam.getText().toString();
                            libreta = cajalibre.getText().toString();
                            tareas = cajahw.getText().toString();
                            participacion = cajaparticip.getText().toString();
                            trabajos = cajatrabajos.getText().toString();
                            asistencia = cajaasist.getText().toString();
                            otros = cajaother.getText().toString();*/



                            Criterioseva gruposrequest = new Criterioseva(namemateria, creador, nameescuela, nivel, claveacc, nombregrupo, responseListener);
                            RequestQueue queue = Volley.newRequestQueue(NextStep.this);
                            queue.add(gruposrequest);
                        }


                }

            });
        }


    }



}
