package com.cejj.sedd.activities;

import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cejj.sedd.R;
import com.cejj.sedd.fragments.AboutFragment;
import com.cejj.sedd.fragments.ConfigFragment;
import com.cejj.sedd.fragments.FeedbackFragment;
import com.cejj.sedd.fragments.PlayFragment;
import com.cejj.sedd.fragments.ProfileFragment;
import com.cejj.sedd.fragments.RankingFragment;
import com.cejj.sedd.fragments.TutorialFragment;

public class DrawerActivity extends AppCompatActivity {

    private ActionBar actionBar;
    private Toolbar toolbar;
    private boolean viewIsAtHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        initToolbar();
        initNavigationMenu();
        displayView(R.id.nav_profile);
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    private void initNavigationMenu() {
        final NavigationView nav_view = (NavigationView) findViewById(R.id.nav_view);
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(final MenuItem item) {

                LinearLayout header = (LinearLayout) findViewById(R.id.nav_profile);
                header.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
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

    public void displayView(int viewId) {

        Fragment fragment = null;
        String title = getString(R.string.app_name);

        switch (viewId) {
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
                fragment = new FeedbackFragment();
                title = "Feedback";
                viewIsAtHome = false;
                break;
            case R.id.nav_config:
                fragment = new ConfigFragment();
                title = "Configuraciones";
                viewIsAtHome = false;
                break;
            case R.id.nav_about:
                fragment = new AboutFragment();
                title = "Acerca";
                viewIsAtHome = false;
                break;
            case R.id.nav_logout:
                Toast.makeText(DrawerActivity.this, "Salir", Toast.LENGTH_LONG).show();
                viewIsAtHome = false;
                break;
            case R.id.nav_profile:
                fragment = new ProfileFragment();
                title = "Perfil";
                viewIsAtHome = true;
                break;
        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        // set the toolbar title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        if (!viewIsAtHome) { //if the current view is not the News fragment
            displayView(R.id.nav_profile); //display the News fragment
        } else {
            moveTaskToBack(true);  //If view is in News fragment, exit application
        }
    }

}
