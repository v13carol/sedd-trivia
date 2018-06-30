package com.cejj.sedd.activities;

import android.app.Dialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cejj.sedd.utils.Tools;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.cejj.sedd.R;

public class EmailVerifyActivity extends AppCompatActivity {

    private TextView verified, backLogin;
    private Button resent;
    private ProgressBar progressBar;
    private ImageButton refresh;
    private FirebaseAuth mAuth;
    private MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_verify);

        player = MediaPlayer.create(this, R.raw.game_song);
        player.setLooping(true); // Set looping
        player.setVolume(100,100);

        mAuth       = FirebaseAuth.getInstance();
        verified    = findViewById(R.id.verificado_textview);
        resent      = findViewById(R.id.btn_reenviar);
        progressBar = findViewById(R.id.progress_bar);
        backLogin   = findViewById(R.id.btn_back_login);
        refresh     = findViewById(R.id.refresh_status_verified);

        //Animacion del refresh
        final RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(1000);
        rotate.setRepeatCount(1);
        rotate.setRepeatMode(1);
        rotate.setInterpolator(new LinearInterpolator());

        //User
        final FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            user.sendEmailVerification();
        }

        Tools.setSystemBarColor(this, android.R.color.white);
        Tools.setSystemBarLight(this);

        //Mejorar sistema de refresh
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user != null) {
                    user.reload().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            refresh.startAnimation(rotate);

                            if (!user.isEmailVerified()) {
                                user.reload();
                            }
                            //TODO: Check if automatically receive the update!
                            if (user.isEmailVerified())
                                showCustomDialog();
                        }
                    });
                }
            }
        });

        resent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resent.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                if (user != null) {
                    user.sendEmailVerification()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    progressBar.setVisibility(View.GONE);
                                    resent.setVisibility(View.VISIBLE);
                                    Snackbar.make(findViewById(android.R.id.content),"¡Correo enviado!",Snackbar.LENGTH_LONG).show();
                                }
                            });
                }
            }
        });

        backLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        mAuth.signOut();
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void showCustomDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_success);
        dialog.setCancelable(false);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        ( dialog.findViewById(R.id.btn_empezar)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                mAuth.signOut();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser() != null)
            mAuth.getCurrentUser().reload();
        player.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mAuth.getCurrentUser() != null)
            mAuth.getCurrentUser().reload();
        player.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAuth.signOut();
        player.stop();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.signOut();
        player.pause();
    }

    @Override
    protected void onPause() {
        super.onPause();
        player.pause();
    }
}
