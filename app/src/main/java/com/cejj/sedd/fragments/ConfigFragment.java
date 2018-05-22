package com.cejj.sedd.fragments;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.cejj.sedd.R;
import com.cejj.sedd.activities.SplashActivity;


public class ConfigFragment extends BaseFragment{

    SplashActivity obj = new SplashActivity();

    private MediaPlayer mediaPlayer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_config, container, false);

        SwitchCompat sonido = (SwitchCompat) view.findViewById(R.id.switch_sonido);
        mediaPlayer = obj.initSonido(getActivity().getApplicationContext());
        sonido.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        if (mediaPlayer.isPlaying()) {
                            mediaPlayer.stop();
                        }else {
                            mediaPlayer.stop();
                        }
                    }
                });
            }
        });

        return view;
    }


}
