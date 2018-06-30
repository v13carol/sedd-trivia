package com.cejj.sedd.activities;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import com.cejj.sedd.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private ProgressBar progress_bar;
    private FloatingActionButton fab;
    private View parent_view;
    public MediaPlayer player;
    public VideoView videoview;
    private EditText Email, Password;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN , WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        //Firebase
        mAuth = FirebaseAuth.getInstance();

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged (@NonNull FirebaseAuth firebaseAuth){
                final FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null && user.isEmailVerified()) {
                    // Profile is signed in
                    Log.i("Login" , "Login");
                    startActivity(new Intent(getApplicationContext() , DrawerActivity.class));
                    finish();
                } else {
                    // Profile is signed out
                    Log.i("Login" , "Logout");
                }
            }
        };

        videoview = (VideoView) findViewById(R.id.videoView);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.bg_video);
        videoview.setVideoURI(uri);
        videoview.start();

        videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared (MediaPlayer mp){
                mp.setLooping(true);
            }
        });

        player = MediaPlayer.create(this , R.raw.game_song);
        player.setLooping(true); // Set looping
        player.setVolume(100 , 100);

        Email = findViewById(R.id.userEmail);
        Password = findViewById(R.id.userPassword);
        parent_view = findViewById(android.R.id.content);
        progress_bar = findViewById(R.id.progress_bar);
        fab = findViewById(R.id.fab);

        ((View) findViewById(R.id.sign_up_for_account)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view){

                Intent registrarIntent = new Intent(LoginActivity.this , SignupActivity.class);
                LoginActivity.this.startActivity(registrarIntent);
            }
        });

        ((View) findViewById(R.id.forgot_password)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view){

                Intent forgotIntent = new Intent(LoginActivity.this , ForgotActivity.class);
                LoginActivity.this.startActivity(forgotIntent);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (final View v){
                userLogin();
            }
        });
    }

    @Override
    protected void onStart (){
        super.onStart();
        player.start();
        mAuth.addAuthStateListener(mAuthStateListener);
        videoview.start();
        if(!haveNetworkConnection()) {
            Snackbar.make(parent_view , "Sin conexión" , Snackbar.LENGTH_INDEFINITE)
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


    private void userLogin (){

        String email = Email.getText().toString().trim();
        String password = Password.getText().toString().trim();

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

        if(password.isEmpty()) {

            Password.setError("Contraseña requerida");
            Password.requestFocus();
            return;
        }

        if(!isValidPassword(password)) {
            Password.setError("Formato no valido");
            Password.requestFocus();
            return;
        }

        progress_bar.setVisibility(View.VISIBLE);
        fab.setAlpha(0f);

        mAuth.signInWithEmailAndPassword(email , password).addOnCompleteListener(new OnCompleteListener <AuthResult>() {
            @Override
            public void onComplete (@NonNull Task <AuthResult> task){
                progress_bar.setVisibility(View.GONE);
                fab.setAlpha(1f);

                if(task.isSuccessful()) {

                    if(mAuth.getCurrentUser().isEmailVerified()) {
                        Intent homeIntent = new Intent(LoginActivity.this , DrawerActivity.class);
                        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(homeIntent);
                        finish();
                    } else {
                        Intent intent = new Intent(LoginActivity.this , EmailVerifyActivity.class);
                        startActivity(intent);
                        finish();
                    }

                } else {
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        Toast.makeText(getApplicationContext() , "Contraseña incorrecta" , Toast.LENGTH_SHORT).show();
                    } catch (FirebaseAuthInvalidUserException e) {
                        Toast.makeText(getApplicationContext() , "Cuenta no esta registrada" , Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext() , "Sin conexion a Internet" , Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

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

    public boolean isValidPassword (final String password){

        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{6,40}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();
    }

    @Override
    public void onBackPressed (){
        if(doubleBackToExitPressedOnce) {
            super.onBackPressed();
            finish();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this , "Otra vez para salir de la app" , Toast.LENGTH_LONG).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run (){
                doubleBackToExitPressedOnce = false;
            }
        } , 2000);
    }
}
