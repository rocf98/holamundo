package com.example.elro.nopapers;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Usuario1 on 16/07/2016.
 */
public class llenardatosrequest extends StringRequest {
    private static final String LOGIN_REQUEST_URL = "http://nopapers0122.esy.es/llenardatos.php";
    private Map<String, String> params;

    public llenardatosrequest(String clavefinal,  Response.Listener<String> listener){
        super(Request.Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("clavefinal", clavefinal);


    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}