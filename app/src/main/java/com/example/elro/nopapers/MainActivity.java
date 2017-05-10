package com.example.elro.nopapers;

import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.ByteArrayOutputStream;
import java.util.Random;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, grupos.OnFragmentInteractionListener, gruposalumnos.OnFragmentInteractionListener, mainfragment.OnFragmentInteractionListener{

    NavigationView navigationView = null;
    Toolbar toolbar= null;
    private GoogleApiClient client;
    private String nombre,tipocuenta,email,usuario,idusu;
    private boolean isStartup = true;
    public String cuentatipo;
    int cuenta;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        nombre = intent.getStringExtra("nombre");
        usuario = intent.getStringExtra("usuario");
        email = intent.getStringExtra("email");
        int age = intent.getIntExtra("age", -1);
        String password = intent.getStringExtra("password");
        cuenta = intent.getIntExtra("cuenta", -1);
        idusu= intent.getStringExtra("idusuario");
        int layoutId=0;

        if(cuenta==1) {
            layoutId = R.layout.activity_main;
        }else if(cuenta==2){
            layoutId = R.layout.activity_mainprofe;
        }else if(cuenta==3){
            layoutId = R.layout.activity_maintutor;
        }


        setContentView(layoutId);

        mainfragment mainfragment = new mainfragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, mainfragment);
        fragmentTransaction.commit();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View hView = navigationView.getHeaderView(0);
        TextView usuarioo = (TextView) hView.findViewById(R.id.nombrecompleto);
        TextView emaill = (TextView) hView.findViewById(R.id.correoelectronico);




        usuarioo.setText(nombre);
        emaill.setText(email);

        if(cuenta==1){
            tipocuenta="alumno";
        }else if(cuenta==2){
            tipocuenta="profesor";
        }else if(cuenta==3){
        tipocuenta="tutor";
    }





        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

    }
    public String generarclave(int longitud){
        String cadenaAleatoria = "";
        long milis = new java.util.GregorianCalendar().getTimeInMillis();
        Random r = new Random(milis);
        int i = 0;
        while ( i < longitud){
            char c = (char)r.nextInt(255);
            if ( (c >= '0' && c <='9') || (c >='A' && c <='Z') ){
                cadenaAleatoria += c;
                i ++;
            }
        }
        return cadenaAleatoria;
    }

    public String getMyData() {
        return nombre;

    }
       public String getMyDatacuenta() {

           return tipocuenta;

    }

    public String getMyDataemail() {
        return email;

    }
    public String getMyDatausuario() {
        return usuario;

    }
    public String getidusu() {
        return idusu;

    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            this.moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_grupos) {   //**IMPORTANTE: nav_grupos se cambia por nav_tutorados en casO de ser la cuenta "Tutor"
            if(tipocuenta.equals("profesor")) {  //Cuenta profesor"
                grupos grupos = new grupos();
                android.support.v4.app.FragmentTransaction fragmentTransaction =
                        getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, grupos);
                fragmentTransaction.commit();

            }else if(tipocuenta.equals("alumno")) {  //Cuenta "alumno"

                gruposalumnos gruposalumnos = new gruposalumnos();
                android.support.v4.app.FragmentTransaction fragmentTransaction =
                       getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, gruposalumnos);
                fragmentTransaction.commit();


            }else if(tipocuenta.equals("tutor")) {  //Cuenta "tutor"

                tutorados tutorados = new tutorados();
                android.support.v4.app.FragmentTransaction fragmentTransaction =
                        getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, tutorados);
                fragmentTransaction.commit();


            }



        }

       else if (id == R.id.nav_horario) {
            if(tipocuenta.equals("profesor")) {

                Intent intent = new Intent(this, horarioprof.class);
                this.startActivity(intent);

            }else if(tipocuenta.equals("alumno")) {


            }else if(tipocuenta.equals("tutor"))  {


            }



        }

        else if (id == R.id.nav_codigoqr) {
            if (tipocuenta.equals("profesor")) {

            } else if (tipocuenta.equals("alumno")) {
                fragqralumn fragqralumn = new fragqralumn();
                android.support.v4.app.FragmentTransaction fragmentTransaction =
                        getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragqralumn);
                fragmentTransaction.commit();


            }


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




    @Override
    public void onStart(){
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.elro.nopapers/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }



    public void grupoabrir (View v){
        Intent intent = new Intent(this, addgrupo.class);
        intent.putExtra("nombre", nombre);
        startActivity(intent);
    }

    public  void cerrarsesion (View v){
        finish();
        Intent intent = new Intent(this, Login_Activity.class);
        this.startActivity(intent);
    }


    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.elro.nopapers/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }


}


