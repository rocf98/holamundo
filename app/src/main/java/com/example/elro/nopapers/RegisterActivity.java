/*package com.example.elro.nopapers;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends Activity {
    String cuenta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);
        final EditText edad = (EditText) findViewById(R.id.age2);
        final EditText name = (EditText) findViewById(R.id.nombre);
        final EditText user = (EditText) findViewById(R.id.nombrecompleto);
        final EditText correo = (EditText) findViewById(R.id.emailreg);
        final EditText contraseña = (EditText) findViewById(R.id.password);
        final Button bregistro= (Button)findViewById(R.id.bregister);

        bregistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String agestring= edad.getText().toString();
                if(agestring.equals("")){

                    edad.setText("0");
                }else
                {}
                final String nombre = name.getText().toString();
                final String usuario = user.getText().toString();
                final int age = Integer.parseInt(edad.getText().toString());
                final String password = contraseña.getText().toString();
                final String email = correo.getText().toString();

                cuenta="profesor";

                Response.Listener<String> responseListener= new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success= jsonResponse.getBoolean("success");
                            if(success){
                                Toast.makeText(getApplicationContext(),
                                        "Registro exitoso", Toast.LENGTH_LONG).show();
                                Intent openlogin = new Intent(RegisterActivity.this, Login_Activity.class);
                                RegisterActivity.this.startActivity(openlogin);
                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("Registro Fallido")
                                        .setNegativeButton("Reintentar", null)
                                        .create()
                                        .show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                if(usuario.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setMessage("Introduzca su usuario")
                            .setNegativeButton("Aceptar", null)
                            .create()
                            .show();
                    edad.setText("");
                }else if(password.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setMessage("Introduzca su contraseña")
                            .setNegativeButton("Aceptar", null)
                            .create()
                            .show();
                    edad.setText("");
                }else if(email.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setMessage("Introduzca su email")
                            .setNegativeButton("Aceptar", null)
                            .create()
                            .show();
                    edad.setText("");
                }else if(nombre.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setMessage("Introduzca su nombre")
                            .setNegativeButton("Aceptar", null)
                            .create()
                            .show();
                    edad.setText("");
                }else if(agestring.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setMessage("Introduzca su edad")
                            .setNegativeButton("Aceptar", null)
                            .create()
                            .show();
                    edad.setText("");

                }else {

                    RegisterRequest registerRequest = new RegisterRequest(nombre, usuario, email, age, password, cuenta,responseListener);
                    RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                    queue.add(registerRequest);
                }


            }
        });
    }

}
*/