package com.example.elro.nopapers;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Crearmateria extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crearmateria);
        final EditText cajamateria = (EditText) findViewById(R.id.materiabox);
        final EditText cajaespecialidad = (EditText) findViewById(R.id.especialidadbox);
        final EditText cajaestudios = (EditText) findViewById(R.id.estudiosbox);
        this.setTitle("Nueva Materia");
        final Button botonnext= (Button)findViewById(R.id.botonmateria);


        if (botonnext != null) {
            botonnext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Response.Listener<String> responseListener = new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                if (success) {
                                     Intent grouponew = new Intent(Crearmateria.this, addgrupo.class);
                                    //   grouponew.putExtra("nombregruponew", nombregrupo);
                                    // grouponew.putExtra("creador2new", creador);
                                    // grouponew.putExtra("claveaccnew", claveacc);
                                     Crearmateria.this.startActivity(grouponew);
                                    finish();
                                    Toast.makeText(Crearmateria.this, "Materia Creada", Toast.LENGTH_SHORT).show();



                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(Crearmateria.this);
                                    builder.setMessage("Error al crear materia")
                                            .setNegativeButton("Reintentar", null)
                                            .create()
                                            .show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    };

                    if (cajamateria.equals("")) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Crearmateria.this);
                        builder.setMessage("Introduzca el nombre de la materua")
                                .setNegativeButton("Aceptar", null)
                                .create()
                                .show();
                        /*
                    } else if (cajaespecialidad.equals("")) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Crearmateria.this);
                        builder.setMessage("Introduzca su materia")
                                .setNegativeButton("Aceptar", null)
                                .create()
                                .show();
                    } else if (cajaestudios.equals("")) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Crearmateria.this);
                        builder.setMessage("Introduzca un plan de estudios")
                                .setNegativeButton("Aceptar", null)
                                .create()
                                .show();*/               //PENDIENTE


                    } else {
                        String materia;
                        String especialidad;
                        String planestudios;


                        materia = cajamateria.getText().toString();
                        especialidad = cajaespecialidad.getText().toString();
                        planestudios = cajaestudios.getText().toString();


                        NewMateriarequest materiasrequest = new NewMateriarequest(materia, especialidad, planestudios, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(Crearmateria.this);
                        queue.add(materiasrequest);

                    }


                }

            });
        }


    }



}
