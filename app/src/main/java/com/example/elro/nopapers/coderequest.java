package com.example.elro.nopapers;



import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;



public class coderequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "http://pruebaschoolactive.esy.es/llenardatos.php";
    private Map<String, String> params;
    private String name;



    public coderequest(String clavefinal,String usuario,String nombre, Response.Listener<String> listener){
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("clavefinal", clavefinal);
        params.put("usuario", usuario);
        params.put("nombre", nombre);







    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}