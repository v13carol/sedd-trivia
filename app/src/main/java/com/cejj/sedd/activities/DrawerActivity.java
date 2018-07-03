package com.cejj.sedd.activities;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cejj.sedd.R;
import com.cejj.sedd.fragments.AboutFragment;
import com.cejj.sedd.fragments.ConfigFragment;
import com.cejj.sedd.fragments.PlayFragment;
import com.cejj.sedd.fragments.ProfileFragment;
import com.cejj.sedd.fragments.RankingFragment;
import com.cejj.sedd.fragments.ReglasFragment;
import com.cejj.sedd.fragments.TutorialFragment;
import com.cejj.sedd.model.Player;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;

public class DrawerActivity extends AppCompatActivity {

    private ActionBar actionBar;
    private Toolbar toolbar;
    private boolean viewIsAtHome;
    public MediaPlayer player;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseDatabase mDB;
    private DatabaseReference mRef;
    private Player user;

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        //Firebase
        mAuth = FirebaseAuth.getInstance();

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged (@NonNull FirebaseAuth firebaseAuth){
                final FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user == null) {
                    // Profile is signed in
                    Log.i("Login" , "Logout");
                    startActivity(new Intent(getApplicationContext() , LoginActivity.class));
                    finish();
                } else {
                    // Profile is signed out
                    Log.i("Login" , "Login " + user.getUid());
                }
            }
        };

        mDB = FirebaseDatabase.getInstance();
        mRef = mDB.getReference();

        mRef.child("Profiles").child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange (@NonNull DataSnapshot dataSnapshot){
                user = dataSnapshot.getValue(Player.class);
                initNavigationMenu();
                displayView(R.id.nav_profile);
            }

            @Override
            public void onCancelled (@NonNull DatabaseError databaseError){

            }
        });

        initToolbar();
        initSound();
    }

    public MediaPlayer initSound (){
        player = MediaPlayer.create(this , R.raw.game_song);
        player.setLooping(true); // Set looping
        player.setVolume(100 , 100);
        return player;
    }

    private void initToolbar (){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    private void initNavigationMenu (){
        final NavigationView nav_view = (NavigationView) findViewById(R.id.nav_view);
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        final View RefView = nav_view.getHeaderView(0);

        CircularImageView profile_img_header = RefView.findViewById(R.id.image_profile);
        TextView profile_name_header = RefView.findViewById(R.id.name_profile);

        //Genero en imagen de perfil
        if(user.getGender().equals("Hombre")) {
            profile_img_header.setImageResource(R.drawable.male_profile_pic);
        } else {
            profile_img_header.setImageResource(R.drawable.female_profile_pic);
        }

        profile_name_header.setText(user.getName());

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this , drawer , toolbar , R.string.navigation_drawer_open , R.string.navigation_drawer_close) {
            public void onDrawerOpened (View drawerView){
                super.onDrawerOpened(drawerView);
            }
        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected (final MenuItem item){

                LinearLayout header = (LinearLayout) findViewById(R.id.nav_profile);

                header.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick (View v){
                        displayView(v.getId());
                        item.setChecked(false);
                    }
                });

                item.setChecked(true);
                displayView(item.getItemId());
                drawer.closeDrawers();
                return true;
            }
        });
    }

    public void displayView (int viewId){

        Fragment fragment = null;
        String title = getString(R.string.app_name);

        switch(viewId) {
            case R.id.nav_play:
                fragment = new PlayFragment();
                title = "Jugar";
                viewIsAtHome = false;
                break;
            case R.id.nav_ranking:
                fragment = new RankingFragment();
                title = "Ranking";
                viewIsAtHome = false;
                break;
            case R.id.nav_tutorial:
                fragment = new TutorialFragment();
                title = "Tutorial";
                viewIsAtHome = false;
                break;
            case R.id.nav_feedback:
                fragment = new ReglasFragment();
                title = "Reglas";
                viewIsAtHome = false;
                break;
            case R.id.nav_config:
                fragment = new ConfigFragment();
                title = "Configuraciones";
                viewIsAtHome = false;
                break;
            case R.id.nav_about:
                fragment = new AboutFragment();
                title = "Créditos";
                viewIsAtHome = false;
                break;
            case R.id.nav_logout:
                viewIsAtHome = false;
                //TODO: Hacer un dialogo para asegurar la accion.
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(DrawerActivity.this , LoginActivity.class));
                finish();
                break;
            case R.id.nav_profile:
                fragment = new ProfileFragment();
                title = "Perfil";
                viewIsAtHome = true;
                break;
        }

        if(fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame , fragment);
            ft.commit();
        }

        // set the toolbar title
        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

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
    protected void onResume (){
        super.onResume();
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
        player.start();
    }

    @Override
    protected void onStop (){
        super.onStop();
        if(mAuthStateListener != null) {
            mAuth.removeAuthStateListener(mAuthStateListener);
        }
        player.pause();
    }

    @Override
    protected void onPause (){
        super.onPause();
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
    public void onBackPressed (){
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if(drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if(!viewIsAtHome) { //if the current view is not the Profile fragment
            displayView(R.id.nav_profile); //display the Profile fragment
        } else {
            moveTaskToBack(true);  //If view is in Profile fragment, exit application
        }
    }

}
