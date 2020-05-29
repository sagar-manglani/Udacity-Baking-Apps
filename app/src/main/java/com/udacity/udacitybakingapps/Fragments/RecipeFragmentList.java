package com.udacity.udacitybakingapps.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.udacitybakingapps.Data.Recipe;
import com.udacity.udacitybakingapps.Interface.FragmentToActivityListener;
import com.udacity.udacitybakingapps.Interface.SendDataFromActivity;
import com.udacity.udacitybakingapps.Interface.FragmentListOnClickListener;
import com.udacity.udacitybakingapps.R;
import com.udacity.udacitybakingapps.RecyclerView.RecipeListAdapter;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecipeFragmentList extends Fragment implements SendDataFromActivity, FragmentListOnClickListener {
    RecyclerView recipeList;
    RecipeListAdapter adapter;
    ArrayList<Recipe> list;
    FragmentToActivityListener listener;
    static String TAG= RecipeFragmentList.class.getSimpleName();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recipelist,container,false);
        recipeList=view.findViewById(R.id.recipe_list);
        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        lm.setOrientation(RecyclerView.VERTICAL);
        recipeList.setLayoutManager(lm);
        adapter=new RecipeListAdapter(getActivity(),this);
        recipeList.setAdapter(adapter);
        adapter.setList(list);
        listener=(FragmentToActivityListener) getActivity();
        //Log.d(TAG,"inside onCreateView "+list.get(0).getName());
        return view;

    }

    @Override
    public void sendData(ArrayList<Recipe> list) {
        if(this.list==null)
            this.list=list;
        else{
            this.list=list;
            adapter.setList(this.list);
        }

    }

    @Override
    public void listOnClick(int position) {
        Log.d(TAG,"clicked on in fragment "+position);
        listener.sendDataToActivity(position);

    }
}
