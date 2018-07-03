package com.cejj.sedd.fragments;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.cejj.sedd.R;
import com.cejj.sedd.activities.SplashActivity;

public class ConfigFragment extends BaseFragment {

    SplashActivity obj = new SplashActivity();

    private MediaPlayer mediaPlayer;

    @Override
    public void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView (LayoutInflater inflater , ViewGroup container ,
                              Bundle savedInstanceState){
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_config , container , false);
        return view;
    }


}
