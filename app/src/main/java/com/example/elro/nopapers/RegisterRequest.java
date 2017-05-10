package com.example.elro.nopapers;

import android.widget.EditText;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by elro on 04/05/2016.
 */
public class RegisterRequest extends StringRequest{
    private static final String REGISTER_REQUEST_URL = "http://pruebaschoolactive.esy.es/Register.php";
    private Map<String, String> params;

    public RegisterRequest(String nombre, String usuario, String email, int age, String password, int cuenta, Response.Listener<String> listener){
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("nombre", nombre);
        params.put("usuario", usuario);
        params.put("email", email);
        params.put("age", age + "");
        params.put("password", password);
        params.put("cuenta", cuenta + "");




    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
