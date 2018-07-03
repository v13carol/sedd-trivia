package com.cejj.sedd.activities;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.cejj.sedd.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ForgotActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private ProgressBar progress_bar;
    private FloatingActionButton fab;
    private EditText Email;
    private MediaPlayer player;

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);

        //Firebase
        mAuth = FirebaseAuth.getInstance();

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged (@NonNull FirebaseAuth firebaseAuth){
                final FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null && user.isEmailVerified()) {
                    // Profile is signed in
                    Log.i("Login" , "Login " + user.getUid());
                    startActivity(new Intent(getApplicationContext() , DrawerActivity.class));
                    finish();
                } else {
                    // Profile is signed out
                    Log.i("Login" , "Logout");
                }
            }
        };


        initToolbar();

        player = MediaPlayer.create(this , R.raw.game_song);
        player.setLooping(true); // Set looping
        player.setVolume(100 , 100);

        progress_bar = findViewById(R.id.progress_bar_forgot);
        fab = findViewById(R.id.fab_forgot);
        Email = findViewById(R.id.reset_password_input);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (final View v){
                resetPassword();
            }
        });
    }

    private void resetPassword (){
        String email = Email.getText().toString().trim();

        if(email.isEmpty()) {

            Email.setError("Correo requerido");
            Email.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Email.setError("Formato invalido");
            Email.requestFocus();
            return;
        }

        progress_bar.setVisibility(View.VISIBLE);
        fab.setVisibility(View.GONE);

        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener <Void>() {
            @Override
            public void onComplete (@NonNull Task <Void> task){
                if(task.isSuccessful()) {

                    progress_bar.setVisibility(View.GONE);
                    fab.setVisibility(View.VISIBLE);
                    Snackbar.make(findViewById(android.R.id.content) , "Correo enviado!" , Snackbar.LENGTH_LONG).show();
                } else {
                    progress_bar.setVisibility(View.GONE);
                    fab.setVisibility(View.VISIBLE);
                    Email.setError("Este correo no esta registrado");
                    Email.requestFocus();
                    Log.i("Error reset" , task.getException().toString());
                }
            }
        });

    }

    @Override
    public void onBackPressed (){

        Intent intent = new Intent(getApplicationContext() , LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void initToolbar (){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Recuperar");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onStart (){
        super.onStart();
        player.start();
        mAuth.addAuthStateListener(mAuthStateListener);
        if(!haveNetworkConnection()) {
            Snackbar.make(findViewById(android.R.id.content) , "Sin conexión" , Snackbar.LENGTH_INDEFINITE)
                    .setAction("CONECTAR" , new View.OnClickListener() {
                        @Override
                        public void onClick (View view){
                            startActivity(new Intent(WifiManager.ACTION_PICK_WIFI_NETWORK));
                        }
                    })
                    .setActionTextColor(getResources().getColor(android.R.color.holo_blue_light))
                    .show();
        }
    }

    @Override
    protected void onPause (){
        super.onPause();
        player.pause();
    }

    @Override
    protected void onResume (){
        super.onResume();
        mAuth.addAuthStateListener(mAuthStateListener);
        player.start();
    }

    @Override
    protected void onRestart (){
        super.onRestart();
        player.start();
    }

    @Override
    public void onStop (){
        super.onStop();

        if(mAuthStateListener != null) {
            mAuth.removeAuthStateListener(mAuthStateListener);
        }
        player.pause();
    }

    //Networking test
    private boolean haveNetworkConnection (){
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for(NetworkInfo ni : netInfo) {
            if(ni.getTypeName().equalsIgnoreCase("WIFI"))
                if(ni.isConnected())
                    haveConnectedWifi = true;
            if(ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if(ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }


    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        if(item.getItemId() == android.R.id.home) {
            finish();
        } else {
            Toast.makeText(getApplicationContext() , item.getTitle() , Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
