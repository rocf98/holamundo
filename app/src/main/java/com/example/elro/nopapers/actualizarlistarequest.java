package com.example.elro.nopapers;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


public class actualizarlistarequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "http://pruebaschoolactive.esy.es/LlenarPaseLista.php";
    private Map<String, String> params;

    public actualizarlistarequest(String idGrupo,String fecha, Response.Listener<String> listener){
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("idGrupo", idGrupo);
        params.put("fecha", fecha);

//morire

//hola tu gfa

//pepepepepe

        //Cesar ya casi se duerme

        //Prueba

        ///Sueña con Valeria

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
