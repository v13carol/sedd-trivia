package com.cejj.sedd.fragments;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cejj.sedd.R;
import com.cejj.sedd.model.Preguntas;
import com.cejj.sedd.utils.ViewAnimation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class TutorialLevel extends BaseFragment {

    private int MAX_STEP = 20;
    private int current_step = 1;
    private ProgressBar progressBar;
    private TextView status;
    private TextView contenido;
    private View mView;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDB;
    private DatabaseReference mRef;
    private String content_description_array[];

    public TutorialLevel (){
        // Required empty public constructor
    }

    public static TutorialLevel newInstance (String title , String nivel){
        Bundle args = new Bundle();
        args.putString("title" , title);
        args.putString("nivel" , nivel);
        TutorialLevel fragment = new TutorialLevel();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView (LayoutInflater inflater , ViewGroup container , Bundle savedInstanceState){
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.tutorial_level , container , false);
        mView = view;
        return view;
    }

    @Override
    public void onViewCreated (View view , @Nullable Bundle savedInstanceState){
        super.onViewCreated(view , savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        mDB = FirebaseDatabase.getInstance();
        mRef = mDB.getReference();

        //TODO: Validar con la instacia que tipo de nivel es.
        mRef.child("Preguntas").child(getArguments().getString("title")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange (@NonNull DataSnapshot dataSnapshot){

                //Get all children
                MAX_STEP = (int) dataSnapshot.getChildrenCount();
                //List
//                List<Preguntas> tutoriales = new ArrayList <>();
                //Control
                content_description_array = new String[MAX_STEP];
                int x = 0;
                //Loop
                for (DataSnapshot dm: dataSnapshot.getChildren()) {
                    //Store all the tutorials
                    Preguntas pregunta = dm.getValue(Preguntas.class);
//                    tutoriales.add(pregunta);
                    content_description_array[x] = pregunta.getTutorial();
                    x++;
                }
                initComponent();
            }

            @Override
            public void onCancelled (@NonNull DatabaseError databaseError){

            }
        });

    }

    private void initComponent (){
        status = (TextView) mView.findViewById(R.id.title);
        contenido = (TextView) mView.findViewById(R.id.description);

        progressBar = (ProgressBar) mView.findViewById(R.id.progress);
        progressBar.setMax(MAX_STEP);
        progressBar.setProgress(current_step);
        progressBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.colorPrimary) , PorterDuff.Mode.SRC_IN);

        ((LinearLayout) mView.findViewById(R.id.lyt_back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view){
                backStep(current_step);
            }
        });

        ((LinearLayout) mView.findViewById(R.id.lyt_next)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view){
                nextStep(current_step);
            }
        });

        String str_progress = String.format(getString(R.string.step_of) , current_step , MAX_STEP);
        status.setText(str_progress);
        contenido.setText(content_description_array[0]);
    }

    private void nextStep (int progress){
        if(progress < MAX_STEP) {
            progress++;
            current_step = progress;
            ViewAnimation.fadeOutIn(status);
        }
        String str_progress = String.format(getString(R.string.step_of) , current_step , MAX_STEP);
        status.setText(str_progress);
        contenido.setText(content_description_array[current_step - 1]);
        progressBar.setProgress(current_step);
    }

    private void backStep (int progress){
        if(progress > 1) {
            progress--;
            current_step = progress;
            ViewAnimation.fadeOutIn(status);
        }
        String str_progress = String.format(getString(R.string.step_of) , current_step , MAX_STEP);
        status.setText(str_progress);
        contenido.setText(content_description_array[current_step - 1]);
        progressBar.setProgress(current_step);
    }

    @Override
    public void onDestroyView (){
        super.onDestroyView();
    }
}