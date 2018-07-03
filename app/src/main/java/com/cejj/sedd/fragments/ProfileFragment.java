package com.cejj.sedd.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cejj.sedd.R;
import com.cejj.sedd.model.Player;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ProfileFragment extends BaseFragment {

    private ImageView profiePic;
    private ImageView Logro1;
    private ImageView Logro2;
    private ImageView Logro3;
    private ImageView Logro4;
    private ImageView Logro5;
    private TextView name;
    private TextView exp;
    private TextView rank;
    private TextView level;
    private TextView completeAnswers;
    private TextView correctAnswers;
    private TextView incorrectAnswers;
    private LinearLayout linearLayout;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDB;
    private DatabaseReference mRef;
    private Player user;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth   = FirebaseAuth.getInstance();
        mDB     = FirebaseDatabase.getInstance();
        mRef    = mDB.getReference();

        mRef.child("Profiles").child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange (@NonNull DataSnapshot dataSnapshot){
                user = dataSnapshot.getValue(Player.class);
                loadProfile();
            }

            @Override
            public void onCancelled (@NonNull DatabaseError databaseError){

            }
        });

        profiePic = view.findViewById(R.id.profile_image);
        name = view.findViewById(R.id.nombre);
        exp = view.findViewById(R.id.experiencia);
        rank = view.findViewById(R.id.rank);
        level = view.findViewById(R.id.nivel);
        completeAnswers = view.findViewById(R.id.preguntas_total);
        correctAnswers = view.findViewById(R.id.correctas);
        incorrectAnswers = view.findViewById(R.id.incorrectas);
        Logro1 = view.findViewById(R.id.image_1);
        Logro2 = view.findViewById(R.id.image_2);
        Logro3 = view.findViewById(R.id.image_3);
        Logro4 = view.findViewById(R.id.image_4);
        Logro5 = view.findViewById(R.id.image_5);
        linearLayout = view.findViewById(R.id.logros_container);

        //Cargar Perfil
    }

    public void loadProfile() {

        //Genero en imagen de perfil
        if(user.getGender().equals("Hombre")){
            profiePic.setImageResource(R.drawable.male_profile_pic);
        } else {
            profiePic.setImageResource(R.drawable.female_profile_pic);
        }

        //Nombre
        name.setText(user.getName());
        //Experiencia
        exp.setText(user.getExp());
        //Rank
        rank.setText(user.getRank());
        //Nivel
        level.setText(user.getLevel());
        //Respuestas totales
        completeAnswers.setText(user.getLvls());
        //Respuestas Correctas
        correctAnswers.setText(user.getCorrect());
        //Respuestas Incorrectas
        incorrectAnswers.setText(user.getWrong());

        //Logros
        if(user.getLogro1().equals("1")){
            Logro1.clearColorFilter();
        } else {
            Logro1.setColorFilter(R.color.overlay_dark_70);
        }

        if(user.getLogro2().equals("1")){
            Logro2.clearColorFilter();
        } else {
            Logro2.setColorFilter(R.color.overlay_dark_70);
        }

        if(user.getLogro3().equals("1")){
            Logro3.clearColorFilter();
        } else {
            Logro3.setColorFilter(R.color.overlay_dark_70);
        }

        if(user.getLogro4().equals("1")){
            Logro4.clearColorFilter();
        } else {
            Logro4.setColorFilter(R.color.overlay_dark_70);
        }

        if(user.getLogro5().equals("1")){
            Logro5.clearColorFilter();
        } else {
            Logro5.setColorFilter(R.color.overlay_dark_70);
        }

        Logro1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                Toast.makeText(getContext() , "Logro 1" , Toast.LENGTH_SHORT).show();
            }
        });

        Logro2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                Toast.makeText(getContext() , "Logro 2" , Toast.LENGTH_SHORT).show();
            }
        });

        Logro3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                Toast.makeText(getContext() , "Logro 3" , Toast.LENGTH_SHORT).show();
            }
        });

        Logro4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                Toast.makeText(getContext() , "Logro 4" , Toast.LENGTH_SHORT).show();
            }
        });

        Logro5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                Toast.makeText(getContext() , "Logro 5" , Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}