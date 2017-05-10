package com.example.elro.nopapers;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by elro on 09/05/2016.
 */


public class gruposrequest extends StringRequest{
    private static final String REGISTER_REQUEST_URL = "http://nopapers0122.esy.es/addgrupo.php";
    private Map<String, String> params;
    private String name;



    public gruposrequest(String nombregrupo, String nivel, String namemateria, String nameescuela, String creador, Response.Listener<String> listener){
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("nombregrupo", nombregrupo);
        params.put("nivel", nivel);
        params.put("namemateria", namemateria);
        params.put("nameescuela", nameescuela);
        params.put("creador", creador);




    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}