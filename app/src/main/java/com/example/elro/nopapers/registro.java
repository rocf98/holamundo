package com.example.elro.nopapers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class registro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

    }

    public void alumnoreg (View v){
        Intent registroalumnpage = new Intent(this, Registeralumno.class);
        this.startActivity(registroalumnpage);
    }

    /*public void profreg (View v){
        Intent registropage = new Intent(this, RegisterActivity.class);
        this.startActivity(registropage);
    }*/
}
