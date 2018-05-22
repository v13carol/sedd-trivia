package com.cejj.sedd.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.cejj.sedd.R;


public class AboutFragment extends BaseFragment {

    private Animation animation;
    private ImageView credits_logo;


    public AboutFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_about, container, false);


        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        animation = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.credits_anim);
        animation.setRepeatCount(Animation.INFINITE);
        view.startAnimation(animation);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
