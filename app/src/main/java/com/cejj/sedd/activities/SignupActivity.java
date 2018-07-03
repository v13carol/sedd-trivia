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
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.cejj.sedd.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText Email, Password, Name, RePassword;
    private FloatingActionButton signUp;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private MediaPlayer player;
    private RadioGroup radioGroup;
    private RadioButton radioSelected;

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

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
        radioGroup = findViewById(R.id.gender);
        Email = findViewById(R.id.correo);
        Password = findViewById(R.id.contra);
        RePassword = findViewById(R.id.recontra);
        signUp = findViewById(R.id.fab_sign_up);
        Name = findViewById(R.id.nombre);
        progressBar = findViewById(R.id.progress_bar);

        signUp.setOnClickListener(this);
    }

    private void initToolbar (){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Resgistrarse");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick (View v){
        switch(v.getId()) {
            case R.id.fab_sign_up:
                registerUser();
                break;
        }
    }

    private void registerUser (){
        final FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference mRef = mDatabase.getReference();
        final String name = Name.getText().toString().trim();
        final String email = Email.getText().toString().trim();
        final String password = Password.getText().toString().trim();
        final String repassword = RePassword.getText().toString().trim();
        final int selectedId = radioGroup.getCheckedRadioButtonId();

        //Gender
        radioSelected = findViewById(selectedId);

        //Name
        if(name.isEmpty()) {
            Name.setError("Nombre requerido");
            Name.requestFocus();
            return;
        }

        if(name.length() < 3) {
            Name.setError("Minimo 3 caracteres");
            Name.requestFocus();
            return;
        }

        //Email
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

        //Password
        if(password.isEmpty()) {
            Password.setError("Contraseña requerida");
            Password.requestFocus();
            return;
        }

        if(!isValidPassword(password)) {
            Password.setError("• Minimo 6 caracteres\n• Minimo 1 Letra\n• Minimo 1 Numero");
            Password.requestFocus();
            return;
        }

        //RePassword
        if(repassword.isEmpty()) {
            RePassword.setError("Escribe la contraseña otra vez");
            RePassword.requestFocus();
            return;
        }

        if(!repassword.equals(password)) {
            RePassword.setError("Contraseñas no coinciden");
            RePassword.requestFocus();
            return;
        }

        if(!isValidPassword(repassword)) {
            RePassword.setError("• Minimo 6 caracteres\n• Minimo 1 Letra\n• Minimo 1 Numero");
            RePassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        signUp.setVisibility(View.GONE);

        mAuth.createUserWithEmailAndPassword(email , password).addOnCompleteListener(new OnCompleteListener <AuthResult>() {
            @Override
            public void onComplete (@NonNull Task <AuthResult> task){

                if(task.isSuccessful()) {
                    String user_id = mAuth.getCurrentUser().getUid();
                    //TODO: Store the extra data and create user
                    Map <String, Object> user = new HashMap <>();
                    user.put("uid" , user_id);
                    user.put("name" , name);
                    user.put("password" , password);
                    user.put("email" , email);
                    user.put("exp" , "0");
                    user.put("lvls" , "0");
                    user.put("rank" , "1");
                    user.put("correct" , "0");
                    user.put("wrong" , "0");
                    user.put("level" , "0");
                    user.put("logro1" , "1");
                    user.put("logro2" , "0");
                    user.put("logro3" , "0");
                    user.put("logro4" , "1");
                    user.put("logro5" , "0");
                    user.put("gender" , radioSelected.getText().toString().trim());
                    //TODO: Agregar los campos que se nesecitan para el juego.
                    //Database
                    mRef.child("Profiles").child(user_id).setValue(user);

                    Intent intent = new Intent(SignupActivity.this , EmailVerifyActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();

                } else if(task.getException() instanceof FirebaseAuthUserCollisionException) {
                    Email.setError("Correo esta registrado");
                    Email.requestFocus();
                } else
                    Log.d("Error Usuario" , "Error " + task.getException().getMessage());

                progressBar.setVisibility(View.GONE);
                signUp.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    protected void onStart (){
        super.onStart();
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
        player.start();
    }

    @Override
    protected void onPause (){
        super.onPause();
        player.pause();
    }

    @Override
    protected void onRestart (){
        super.onRestart();
        mAuth.addAuthStateListener(mAuthStateListener);
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
        Intent intent = new Intent(getApplicationContext() , LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        if(item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
