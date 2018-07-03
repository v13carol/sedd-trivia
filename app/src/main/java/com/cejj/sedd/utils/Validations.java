package com.cejj.sedd.utils;

import android.support.annotation.NonNull;

import com.cejj.sedd.model.Player;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class Validations {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase mDB = FirebaseDatabase.getInstance();
    private DatabaseReference mRef = mDB.getReference();
    private String PLAYER_EXP;
    private String PLAYER_CORRECT_ANSWERS;
    private String PLAYER_LEVEL;
    private String PLAYER_TOTAL_ANSWERS;
    private String PLAYER_TOP_RANK;

    public Validations (){

    }

    //Trofeos
    public void validateTrophies() {

        Map<String, Object> user = new HashMap<>();

        //Validate
        if(Integer.parseInt(PLAYER_EXP) >= 1000) {
            user.put("logro1", "1");
            mRef.child("Profiles").child(mAuth.getCurrentUser().getUid()).updateChildren(user);
        }


        if(PLAYER_LEVEL.equals("5")) {
            user.put("logro2", "1");
            mRef.child("Profiles").child(mAuth.getCurrentUser().getUid()).updateChildren(user);
        }


        if(Integer.parseInt(PLAYER_CORRECT_ANSWERS) >= 100) {
            user.put("logro3", "1");
            mRef.child("Profiles").child(mAuth.getCurrentUser().getUid()).updateChildren(user);
        }

        if(PLAYER_TOP_RANK.equals("1") || PLAYER_TOP_RANK.equals("2") || PLAYER_TOP_RANK.equals("3") ) {
            user.put("logro4", "1");
            mRef.child("Profiles").child(mAuth.getCurrentUser().getUid()).updateChildren(user);
        }


        if(Integer.parseInt(PLAYER_TOTAL_ANSWERS) >= 150) {
            user.put("logro5", "1");
            mRef.child("Profiles").child(mAuth.getCurrentUser().getUid()).updateChildren(user);
        }

        validatePlayerLevel();
    }

    //Nivel personal
    public void validatePlayerLevel() {

        Map<String, Object> user = new HashMap<>();

        //Get EXP
        getEXP();
        //Validate
        if(Integer.parseInt(PLAYER_EXP) >= 500 && Integer.parseInt(PLAYER_EXP) < 1000) {
            user.put("level", "1");
            mRef.child("Profiles").child(mAuth.getCurrentUser().getUid()).updateChildren(user);
        }

        if(Integer.parseInt(PLAYER_EXP) >= 1000 && Integer.parseInt(PLAYER_EXP) < 1500) {
            user.put("level", "2");
            mRef.child("Profiles").child(mAuth.getCurrentUser().getUid()).updateChildren(user);
        }

        if(Integer.parseInt(PLAYER_EXP) >= 1500 && Integer.parseInt(PLAYER_EXP) < 2000) {
            user.put("level", "3");
            mRef.child("Profiles").child(mAuth.getCurrentUser().getUid()).updateChildren(user);
        }

        if(Integer.parseInt(PLAYER_EXP) >= 2000 && Integer.parseInt(PLAYER_EXP) < 3000) {
            user.put("level", "4");
            mRef.child("Profiles").child(mAuth.getCurrentUser().getUid()).updateChildren(user);
        }

        if(Integer.parseInt(PLAYER_EXP) >= 3000) {
            user.put("level", "5");
            mRef.child("Profiles").child(mAuth.getCurrentUser().getUid()).updateChildren(user);
        }
    }

    public void getEXP () {

        mRef.child("Profiles").child(mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange (@NonNull DataSnapshot dataSnapshot) {

                Player player = dataSnapshot.getValue(Player.class);

                if(player != null) {

                    PLAYER_EXP = player.getExp();
                    PLAYER_CORRECT_ANSWERS = player.getCorrect();
                    PLAYER_LEVEL = player.getLevel();
                    PLAYER_TOP_RANK = player.getRank();
                    PLAYER_TOTAL_ANSWERS = player.getLvls();
                }

                validateTrophies();
            }

            @Override
            public void onCancelled (@NonNull DatabaseError databaseError){

            }
        });

    }

}
