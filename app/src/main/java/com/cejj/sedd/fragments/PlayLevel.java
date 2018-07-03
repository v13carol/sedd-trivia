package com.cejj.sedd.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cejj.sedd.R;
import com.cejj.sedd.model.Player;
import com.cejj.sedd.model.Preguntas;
import com.cejj.sedd.utils.Validations;
import com.github.lzyzsd.circleprogress.DonutProgress;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PlayLevel extends BaseFragment {

    private Button play;
    private Button optionA;
    private Button optionB;
    private Button optionC;
    private Button optionD;
    private TextView question;
    private DonutProgress donutProgress;
    private String preguntas_array[] = new String[5];
    private String respuesta_correcta_array[] = new String[5];
    private String respuesta_incorrecta_1_array[] = new String[5];
    private String respuesta_incorrecta_2_array[] = new String[5];
    private String respuesta_incorrecta_3_array[] = new String[5];
    private int count = 0;
    private CountDownTimer timer;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDB;
    private DatabaseReference mRef;
    private int TRIES;

    private int TOTAL_COUNT;
    private int CORRECT_COUNT;
    private int INCORRECT_COUNT;
    private int EXP_COUNT;
    private int valuePerLvl;

    private Validations validar = new Validations();

    //TODO: Crear clase para validar Niveles y Trofeos.

    public PlayLevel (){
        // Required empty public constructor
    }

    public static PlayLevel newInstance (String title , String nivel){
        Bundle args = new Bundle();
        args.putString("title" , title);
        args.putString("nivel" , nivel);
        PlayLevel fragment = new PlayLevel();
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
        View view = inflater.inflate(R.layout.play_level , container , false);
        return view;
    }

    @Override
    public void onViewCreated (View view , @Nullable Bundle savedInstanceState){
        super.onViewCreated(view , savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        mDB = FirebaseDatabase.getInstance();
        mRef = mDB.getReference();

        //Inicializar
        TRIES = 0;

        if(getArguments().getString("title").equals("Nivel 1")) {
            valuePerLvl = 10;
        } else if(getArguments().getString("title").equals("Nivel 2")) {
            valuePerLvl = 15;
        } else if(getArguments().getString("title").equals("Nivel 3")) {
            valuePerLvl = 20;
        }

        getAnswers();

        play = view.findViewById(R.id.play_button);
        optionA = view.findViewById(R.id.OptionA);
        optionB = view.findViewById(R.id.OptionB);
        optionC = view.findViewById(R.id.OptionC);
        optionD = view.findViewById(R.id.OptionD);
        question = view.findViewById(R.id.Questions);
        donutProgress = view.findViewById(R.id.donut_progress);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){

                play.setVisibility(View.GONE);
                optionA.setVisibility(View.VISIBLE);
                optionB.setVisibility(View.VISIBLE);
                optionC.setVisibility(View.VISIBLE);
                optionD.setVisibility(View.VISIBLE);
                donutProgress.setVisibility(View.VISIBLE);

                TOTAL_COUNT = 0;
                CORRECT_COUNT = 0;
                INCORRECT_COUNT = 0;
                EXP_COUNT = 0;
                count = 0;

                setQuestion();
            }
        });

        optionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                if(optionA.getText().equals(respuesta_correcta_array[count])) {
                    //Cancel the timer
                    timer.cancel();
                    //Sumar EXP
                    EXP_COUNT = EXP_COUNT + valuePerLvl;
                    //Sumar Correcta
                    CORRECT_COUNT++;
                    //Sumar Completadas
                    TOTAL_COUNT++;
                    //Mostrar dialogo de correcto
                    showCorrectAnswerDialog();

                } else {
                    //Cancel the timer
                    timer.cancel();
                    //Sumar Incorrectas
                    INCORRECT_COUNT++;
                    //Sumar Completadas
                    TOTAL_COUNT++;
                    //Mostrar dialogo incorrecto
                    showIncorrectAnswerDialog();
                }
            }
        });

        optionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                if(optionB.getText().equals(respuesta_correcta_array[count])) {
                    //Cancel the timer
                    timer.cancel();
                    //Sumar EXP
                    EXP_COUNT = EXP_COUNT + valuePerLvl;
                    //Sumar Correcta
                    CORRECT_COUNT++;
                    //Sumar Completadas
                    TOTAL_COUNT++;
                    //Mostrar dialogo de correcto
                    showCorrectAnswerDialog();

                } else {
                    //Cancel the timer
                    timer.cancel();
                    //Sumar Incorrectas
                    INCORRECT_COUNT++;
                    //Sumar Completadas
                    TOTAL_COUNT++;
                    //Mostrar dialogo incorrecto
                    showIncorrectAnswerDialog();
                }
            }
        });

        optionC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                if(optionC.getText().equals(respuesta_correcta_array[count])) {
                    //Cancel the timer
                    timer.cancel();
                    //Sumar EXP
                    EXP_COUNT = EXP_COUNT + valuePerLvl;
                    //Sumar Correcta
                    CORRECT_COUNT++;
                    //Sumar Completadas
                    TOTAL_COUNT++;
                    //Mostrar dialogo de correcto
                    showCorrectAnswerDialog();

                } else {
                    //Cancel the timer
                    timer.cancel();
                    //Sumar Incorrectas
                    INCORRECT_COUNT++;
                    //Sumar Completadas
                    TOTAL_COUNT++;
                    //Mostrar dialogo incorrecto
                    showIncorrectAnswerDialog();
                }
            }
        });

        optionD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                if(optionD.getText().equals(respuesta_correcta_array[count])) {
                    //Cancel the timer
                    timer.cancel();
                    //Sumar EXP
                    EXP_COUNT = EXP_COUNT + valuePerLvl;
                    //Sumar Correcta
                    CORRECT_COUNT++;
                    //Sumar Completadas
                    TOTAL_COUNT++;
                    //Mostrar dialogo de correcto
                    showCorrectAnswerDialog();

                } else {
                    //Cancel the timer
                    timer.cancel();
                    //Sumar Incorrectas
                    INCORRECT_COUNT++;
                    //Sumar Completadas
                    TOTAL_COUNT++;
                    //Mostrar dialogo incorrecto
                    showIncorrectAnswerDialog();
                }
            }
        });
    }

    public void setQuestion (){

        if(count < 5) {

            //Store the answers
            List <String> answers = new ArrayList <>();
            answers.add(respuesta_correcta_array[count]);
            answers.add(respuesta_incorrecta_1_array[count]);
            answers.add(respuesta_incorrecta_2_array[count]);
            answers.add(respuesta_incorrecta_3_array[count]);

            //Random the answers
            Collections.shuffle(answers);
            Collections.shuffle(answers);
            Collections.shuffle(answers);

            //Set the question
            question.setText(preguntas_array[count]);

            //Set the answers shuffles
            optionA.setText(answers.get(0));
            optionB.setText(answers.get(1));
            optionC.setText(answers.get(2));
            optionD.setText(answers.get(3));

            //Set the timer
            timer();

        } else {
            //If already had 5 questions answered then quit and evaluate the result, Update the player database
            timer.cancel();
            //Pasar parametros para mostrar en dialogo
            showResultDialog();

        }
    }

    public void timer (){

        timer = new CountDownTimer(22000 , 1000) {
            int i = 105;

            public void onTick (long millisUntilFinished){
                i = i - 5;
                donutProgress.setProgress(i);
            }

            public void onFinish (){

                if(timer != null)
                    timer.cancel();
                count = 0;
                showTimeoutDialog();
            }
        };

        timer.start();
    }

    //Dialog Correct Answer
    private void showCorrectAnswerDialog (){
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_correct);
        dialog.setCancelable(false);

        ((TextView) dialog.findViewById(R.id.value_exp)).setText(String.valueOf(valuePerLvl));

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        (dialog.findViewById(R.id.btn_siguiente_pregunta)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                //Sumar 1 en count
                count++;
                //Volver a llamar el metodo.
                setQuestion();

                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    //Dialog Incorrect Answer
    private void showIncorrectAnswerDialog (){
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_incorrect);
        dialog.setCancelable(false);

        final TextView respuesta = (TextView) dialog.findViewById(R.id.respuesta);
        final ImageView imageView1 = (ImageView) dialog.findViewById(R.id.icon_1);
        final ImageView imageView2 = (ImageView) dialog.findViewById(R.id.icon_2);
        final ImageView imageView3 = (ImageView) dialog.findViewById(R.id.icon_3);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        //Aumentar intentos
        //TODO: Revisar sistema de intentos
        TRIES++;
        //Inicializar respuesta e Intento de con imagenes
        respuesta.setText(respuesta_correcta_array[count]);

        //Evaluar cuantas si se equivoco 3 veces.
        if(TRIES == 1) {
            imageView1.setColorFilter(R.color.red_900);
        } else if(TRIES == 2) {
            imageView1.setColorFilter(R.color.red_900);
            imageView2.setColorFilter(R.color.red_900);
        } else if(TRIES == 3) {
            imageView1.setColorFilter(R.color.red_900);
            imageView2.setColorFilter(R.color.red_900);
            imageView3.setColorFilter(R.color.red_900);
        }

        (dialog.findViewById(R.id.btn_siguiente_pregunta)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){

                if(TRIES != 3) {
                    //Sumar 1 en count
                    count++;
                    //Volver a llamar el metodo.
                    setQuestion();
                    //Pasar a la siguiente pregunta
                } else {
                    showResultDialog();
                }

                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    //Dialog Timeout
    private void showTimeoutDialog (){
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_timeout);
        dialog.setCancelable(false);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        (dialog.findViewById(R.id.btn_aceptar)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                FragmentTransaction ft = ((AppCompatActivity) getActivity()).getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame , new PlayFragment());
                dialog.dismiss();
                ft.commit();
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    //Dialog Timeout
    private void showResultDialog (){
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_result);
        dialog.setCancelable(false);

        //Asignar los parametros a los text
        //Buscar las variables
        ((TextView) dialog.findViewById(R.id.preguntas_cant)).setText(String.valueOf(TOTAL_COUNT));
        ((TextView) dialog.findViewById(R.id.cant_correctas)).setText(String.valueOf(CORRECT_COUNT));
        ((TextView) dialog.findViewById(R.id.incorrectas_cant)).setText(String.valueOf(INCORRECT_COUNT));
        ((TextView) dialog.findViewById(R.id.experiencia_total)).setText(String.valueOf(EXP_COUNT));

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;

        (dialog.findViewById(R.id.btn_regresar_menu)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){

                //Update the results in database
                setAtributos();
                //Validar exp = nivel y Exp = trofeos
                validar.getEXP();
                //Exit the level
                FragmentTransaction ft = ((AppCompatActivity) getActivity()).getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame , new PlayFragment());
                dialog.dismiss();
                ft.commit();
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    public void setAtributos (){

        mRef.child("Profiles").child(mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange (@NonNull DataSnapshot dataSnapshot){

                Player player = dataSnapshot.getValue(Player.class);
                Map <String, Object> user = new HashMap <>();

                //Update Total
                user.put("lvls" , String.valueOf((Integer.parseInt(player.getLvls()) + TOTAL_COUNT)));
                //Update Correctas
                user.put("correct" , String.valueOf((Integer.parseInt(player.getCorrect()) + CORRECT_COUNT)));
                //Update Incorrectas
                user.put("wrong" , String.valueOf((Integer.parseInt(player.getWrong()) + INCORRECT_COUNT)));
                //Update Exp
                user.put("exp" , String.valueOf((Integer.parseInt(player.getExp()) + EXP_COUNT)));

                //Update
                mRef.child("Profiles").child(player.getUid()).updateChildren(user);
            }

            @Override
            public void onCancelled (@NonNull DatabaseError databaseError){

            }
        });

    }

    public void getAnswers (){

        mRef.child("Preguntas").child(getArguments().getString("title")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange (@NonNull DataSnapshot dataSnapshot){

                //List
                List <Preguntas> preguntas = new ArrayList <>();
                //Loop
                for(DataSnapshot dm : dataSnapshot.getChildren()) {
                    //Store all the tutorials
                    Preguntas pregunta = dm.getValue(Preguntas.class);
                    preguntas.add(pregunta);
                }
                //Shuffle de preguntas
                Collections.shuffle(preguntas);

                //Get the 5 random questions
                if(preguntas.size() > 5) {

                    List <Integer> index = new ArrayList <>();

                    for(int x = 0; x < preguntas.size(); x++)
                        index.add(x);
                    //Random the index
                    Collections.shuffle(index);

                    for(int i = 0; i < 5; i++) {
                        //Validar la repeticion de indice
                        preguntas_array[i] = preguntas.get(index.get(i)).getPregunta();
                        respuesta_correcta_array[i] = preguntas.get(index.get(i)).getRespuestaCorrecta();
                        respuesta_incorrecta_1_array[i] = preguntas.get(index.get(i)).getRespuestaIncorrecta1();
                        respuesta_incorrecta_2_array[i] = preguntas.get(index.get(i)).getRespuestaIncorrecta2();
                        respuesta_incorrecta_3_array[i] = preguntas.get(index.get(i)).getRespuestaIncorrecta3();
                    }

                } else {
                    Toast.makeText(getContext() , "No existen sufienctes preguntas en la DB" , Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled (@NonNull DatabaseError databaseError){

            }
        });
    }

    @Override
    public void onDetach (){
        super.onDetach();
        onDestroyView();
    }

    @Override
    public void onDestroyView (){
        super.onDestroyView();
        if(timer != null)
            timer.cancel();
    }
}