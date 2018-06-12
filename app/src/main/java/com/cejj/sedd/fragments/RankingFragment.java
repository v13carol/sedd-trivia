package com.cejj.sedd.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.os.Bundle;
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
import com.cejj.sedd.utils.ItemAnimation;
import com.cejj.sedd.utils.Tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class RankingFragment extends BaseFragment{

    private RecyclerView recyclerView;
    private AdapterListAnimation mAdapter;
    private List<People> items = new ArrayList<>();
    private int animation_type = ItemAnimation.BOTTOM_UP;

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
    }

    private void initComponent(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        items = getPeopleData(getContext());
        items.addAll(getPeopleData(getContext()));
        items.addAll(getPeopleData(getContext()));
        items.addAll(getPeopleData(getContext()));
        items.addAll(getPeopleData(getContext()));

        showSingleChoiceDialog();
    }

    private void setAdapter() {
        //set data and list adapter
        mAdapter = new AdapterListAnimation(getContext(), items, animation_type);
        recyclerView.setAdapter(mAdapter);

        // on item list clicked
        mAdapter.setOnItemClickListener(new AdapterListAnimation.OnItemClickListener() {
            @Override
            public void onItemClick(View view, People obj, int position) {
                Snackbar.make(view, "Item " + obj.name + " clicked", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    public static List<People> getPeopleData(Context ctx) {
        List<People> items = new ArrayList<>();
        TypedArray drw_arr = ctx.getResources().obtainTypedArray(R.array.people_images);
        String name_arr[] = ctx.getResources().getStringArray(R.array.people_names);

        for (int i = 0; i < drw_arr.length(); i++) {
            People obj = new People();
            obj.image = drw_arr.getResourceId(i, -1);
            obj.name = name_arr[i];
            obj.email = Tools.getEmailFromName(obj.name);
            obj.imageDrw = ctx.getResources().getDrawable(obj.image);
            items.add(obj);
        }
        Collections.shuffle(items);
        return items;
    }

    private static final String[] ANIMATION_TYPE = new String[]{
            "Bottom Up", "Fade In", "Left to Right", "Right to Left"
    };

    private void showSingleChoiceDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Animation Type");
        builder.setCancelable(false);
        builder.setSingleChoiceItems(ANIMATION_TYPE, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String selected = ANIMATION_TYPE[i];
                if (selected.equalsIgnoreCase("Bottom Up")) {
                    animation_type = ItemAnimation.BOTTOM_UP;
                } else if (selected.equalsIgnoreCase("Fade In")) {
                    animation_type = ItemAnimation.FADE_IN;
                } else if (selected.equalsIgnoreCase("Left to Right")) {
                    animation_type = ItemAnimation.LEFT_RIGHT;
                } else if (selected.equalsIgnoreCase("Right to Left")) {
                    animation_type = ItemAnimation.RIGHT_LEFT;
                }
                setAdapter();
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }
}
