package com.cejj.sedd.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cejj.sedd.R;


public class ProfileFragment extends BaseFragment {


    public ProfileFragment() {
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

        View view = inflater.inflate(R.layout.fragment_profile, container, false);


        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


//        btnClickMe.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (mFragmentNavigation != null) {
//                    mFragmentNavigation.pushFragment(PlayFragment.newInstance(fragCount + 1));
//
//                }
//            }
//        });
//
//
//        ( (MainActivity)getActivity()).updateToolbarTitle((fragCount == 0) ? "Home" : "Sub Home "+fragCount);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}