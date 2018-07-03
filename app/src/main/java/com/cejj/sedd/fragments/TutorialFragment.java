package com.cejj.sedd.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cejj.sedd.R;


public class TutorialFragment extends BaseFragment implements View.OnClickListener {

    private FloatingActionButton play_lvl_one;
    private FloatingActionButton play_lvl_two;
    private FloatingActionButton play_lvl_three;
    private View mView;

    public TutorialFragment (){
        // Required empty public constructor
    }

    @Override
    public void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView (LayoutInflater inflater , ViewGroup container , Bundle savedInstanceState){
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tutorial , container , false);
        mView = view;
        return view;
    }

    @Override
    public void onViewCreated (View view , @Nullable Bundle savedInstanceState){
        super.onViewCreated(view , savedInstanceState);

        play_lvl_one = view.findViewById(R.id.lvlone);
        play_lvl_two = view.findViewById(R.id.lvltwo);
        play_lvl_three = view.findViewById(R.id.lvlthree);

        play_lvl_three.setOnClickListener(this);
        play_lvl_two.setOnClickListener(this);
        play_lvl_one.setOnClickListener(this);
    }

    @Override
    public void onClick (View v){

        TutorialLevel fragment = null;
        String title = "";

        switch(v.getId()) {
            case R.id.lvlone:
                //Create new instance of the tutorial for lvl 1
                fragment = TutorialLevel.newInstance("Nivel 1" , "1");
                title = "Nivel 1";
                break;
            case R.id.lvltwo:
                //Create new instance of the tutorial for lvl 2
                fragment = TutorialLevel.newInstance("Nivel 2" , "2");
                title = "Nivel 2";
                break;
            case R.id.lvlthree:
                //Create new instance of the tutorial for lvl 3
                fragment = TutorialLevel.newInstance("Nivel 3" , "3");
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
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Tutorial de "+title);
        }
    }

    @Override
    public void onDestroyView (){
        super.onDestroyView();
    }
}