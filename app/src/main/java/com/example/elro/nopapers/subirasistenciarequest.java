package com.example.elro.nopapers;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Usuario1 on 21/07/2016.
 */
public class subirasistenciarequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "http://pruebaschoolactive.esy.es/PasarLista1.php";
    private Map<String, String> params;
    private String name;



    public subirasistenciarequest(String nombreusuario, String idGrupo,String fecha, Response.Listener<String> listener){
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("nombreusuario", nombreusuario);
        params.put("idGrupo", idGrupo);
        params.put("fecha",fecha);









    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}