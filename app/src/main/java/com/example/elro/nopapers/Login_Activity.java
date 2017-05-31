package com.example.elro.nopapers;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Login_Activity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        final EditText usuarioo = (EditText) findViewById(R.id.usernamelog);
        final EditText contraseña = (EditText) findViewById(R.id.passwordlog);
        final Button blogin= (Button)findViewById(R.id.blogin);
        final TextView registerlink= (TextView) findViewById(R.id.tvregisterhere);




        registerlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registropage = new Intent(Login_Activity.this, Registeralumno.class);
                Login_Activity.this.startActivity(registropage);
            }
        });
        blogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String usuario = usuarioo.getText().toString();
                final String password = contraseña.getText().toString();
                Response.Listener<String> responseListener = new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            int codigo = jsonResponse.getInt("codigo");
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){

                                if(codigo==1){
                                String nombre = jsonResponse.getString("nombre");
                                String email = jsonResponse.getString("email");
                                String usuario = jsonResponse.getString("usuario");
                                int edad = jsonResponse.getInt("age");
                                String password = jsonResponse.getString("password");
                                int cuenta = jsonResponse.getInt("cuenta");
                                String idusuario = jsonResponse.getString("idusuario");
                                String clave = jsonResponse.getString("clave");



                                Intent intent = new Intent(Login_Activity.this, MainActivity.class);
                                    intent.putExtra("nombre", nombre);
                                    intent.putExtra("usuario", usuario);
                                    intent.putExtra("age", edad);
                                    intent.putExtra("email", email);
                                    intent.putExtra("cuenta", cuenta);
                                    intent.putExtra("idusuario", idusuario);
                                    intent.putExtra("clave", clave);

                                    Login_Activity.this.startActivity(intent);

                                finish();

                                }else{
                                    Toast.makeText(getApplicationContext(),
                                            "Debes verificar tu cuenta, te enviamos un correo", Toast.LENGTH_LONG).show();
                                }
                            }else{

                                Toast.makeText(getApplicationContext(),
                                        "Usuario o contraseña incorrectos", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                };
                LoginRequest loginRequest = new LoginRequest(usuario, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(Login_Activity.this);
                queue.add(loginRequest);
            }
        });


    }
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
        {
            this.moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void prueba (View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void padre (View v){
        Intent intent = new Intent(this, Padresinterfaz.class);
        startActivity(intent);
    }

}
