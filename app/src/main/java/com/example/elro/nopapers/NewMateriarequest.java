package com.example.elro.nopapers;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Irwing PC on 25/09/2016.
 */
public class NewMateriarequest extends StringRequest{
    private static final String REGISTER_REQUEST_URL = "http://schoolactive.xyz/CreacionMaterias.php";
    private Map<String, String> params;




    public NewMateriarequest(String materia, String especialidad, String planestudios, Response.Listener<String> listener){
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("materia",materia);
        params.put("especialidad", especialidad);
        params.put("planestudios", planestudios);


    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

}


