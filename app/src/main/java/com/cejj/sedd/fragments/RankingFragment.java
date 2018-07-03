package com.cejj.sedd.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cejj.sedd.R;
import com.cejj.sedd.adapter.AdapterListAnimation;
import com.cejj.sedd.model.People;
import com.cejj.sedd.model.Player;
import com.cejj.sedd.utils.ItemAnimation;
import com.cejj.sedd.utils.Tools;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RankingFragment extends BaseFragment{

    private RecyclerView recyclerView;
    private AdapterListAnimation mAdapter;
    private List<People> items = new ArrayList<>();
    private int animation_type = ItemAnimation.BOTTOM_UP;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDB;
    private DatabaseReference mRef;
    private Player player;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ranking, container, false);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponent(view);
        mAuth = FirebaseAuth.getInstance();
        mDB     = FirebaseDatabase.getInstance();
        mRef    = mDB.getReference();
        final List<People> items = new ArrayList<>();

        mRef.child("Profiles").orderByChild("exp").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange (@NonNull DataSnapshot dataSnapshot){

                //Take the index
                int index = (int) dataSnapshot.getChildrenCount();

                for (DataSnapshot dm: dataSnapshot.getChildren()) {

                    People obj = new People();
                    Player player = dm.getValue(Player.class);
                    Map<String, Object> user = new HashMap <>();

                    if(index != 0) {
                        //Asigna el indice
                        user.put("rank", String.valueOf(index));
                        //Index disminuye
                        index--;
                    }

                    //Update
                    mRef.child("Profiles").child(player.getUid()).updateChildren(user);

                    if(player.getGender().equals("Hombre"))
                        obj.image = R.drawable.male_profile_pic;
                    else
                        obj.image = R.drawable.female_profile_pic;

                    obj.name = player.getName().trim();
                    obj.exp = player.getExp();

                    items.add((obj));
                }

                //Voltear la lista
                Collections.reverse(items);

                //set data and list adapter
                mAdapter = new AdapterListAnimation(getContext(), items, ItemAnimation.BOTTOM_UP);
                recyclerView.setAdapter(mAdapter);

                // on item list clicked
                mAdapter.setOnItemClickListener(new AdapterListAnimation.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, People obj, int position) {
                        Snackbar.make(view, "Item " + obj.name + " clicked", Snackbar.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onCancelled (@NonNull DatabaseError databaseError){

            }
        });
    }

    private void initComponent(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
    }


}
