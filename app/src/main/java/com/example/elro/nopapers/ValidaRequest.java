package com.example.elro.nopapers;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by elro on 04/05/2016.
 */
public class ValidaRequest extends StringRequest{
    private static final String LOGIN_REQUEST_URL = "http://nopapers0122.esy.es/ValiAlta.php";
    private Map<String, String> params;

    public ValidaRequest(String claveacc,  Response.Listener<String> listener){
        super(Request.Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("claveacc", claveacc);


    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
