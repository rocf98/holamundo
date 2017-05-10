package com.example.elro.nopapers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

public class splash_screen extends Activity {
    public static final int segundos=5;
    public static final int milisegundos=segundos*1000;
    public static final int delay=2;
    private ProgressBar progreso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);
        progreso = (ProgressBar) findViewById(R.id.progressBar);
        progreso.setMax(maximo_progreso());
        empezaranimacion();

    }
    public void  empezaranimacion(){
        new CountDownTimer(milisegundos,1000){

            @Override
            public void onTick(long millisUntilFinished) {
                progreso.setProgress(establecer_progreso(millisUntilFinished));

            }

            @Override
            public void onFinish() {
                Intent ventanaprincipal= new Intent(splash_screen.this,Login_Activity.class);
                startActivity(ventanaprincipal);
                finish();



            }
        }.start();
    }
    public int establecer_progreso(long miliseconds){
        return (int)((milisegundos-miliseconds)/1000);

    }
    public int maximo_progreso(){
        return segundos-delay;
    }
}
