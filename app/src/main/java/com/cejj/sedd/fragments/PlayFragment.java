package com.cejj.sedd.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cejj.sedd.R;


public class PlayFragment extends BaseFragment implements View.OnClickListener{

    public PlayFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_play, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((CardView)view.findViewById(R.id.nivel1)).setOnClickListener(this);

        ((CardView)view.findViewById(R.id.nivel2)).setOnClickListener(this);

        ((CardView)view.findViewById(R.id.nivel3)).setOnClickListener(this);
    }

    @Override
    public void onClick (View v){

        PlayLevel fragment = null;
        String title = "";

        switch(v.getId()) {
            case R.id.nivel1:
                //Create new instance of the tutorial for lvl 1
                fragment = PlayLevel.newInstance("Nivel 1" , "1");
                title = "Nivel 1";
                break;
            case R.id.nivel2:
                //Create new instance of the tutorial for lvl 2
                fragment = PlayLevel.newInstance("Nivel 2" , "2");
                title = "Nivel 2";
                break;
            case R.id.nivel3:
                //Create new instance of the tutorial for lvl 3
                fragment = PlayLevel.newInstance("Nivel 3" , "3");
                title = "Nivel 3";
                break;
            default:
                Toast.makeText(getContext() , "Nada seleccionado" , Toast.LENGTH_SHORT).show();
        }

        if(fragment != null) {
            FragmentTransaction ft = ((AppCompatActivity)getActivity()).getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame , fragment);
            ft.commit();
        }

        // set the toolbar title
        if(((AppCompatActivity)getActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Jugar "+title);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
