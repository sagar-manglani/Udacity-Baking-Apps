package com.udacity.udacitybakingapps.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.udacitybakingapps.Data.Recipe;
import com.udacity.udacitybakingapps.Interface.FragmentListOnClickListener;
import com.udacity.udacitybakingapps.Interface.FragmentToActivityListener;
import com.udacity.udacitybakingapps.Interface.SendDataFromActivity;
import com.udacity.udacitybakingapps.R;
import com.udacity.udacitybakingapps.RecyclerView.RecipeDetailListAdapter;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecipeDetailsFragmentsList extends Fragment implements SendDataFromActivity, FragmentListOnClickListener {
    RecyclerView recipedetail;
    Recipe recipe;
    RecipeDetailListAdapter adapter;
    FragmentToActivityListener activity;
    private static String TAG=RecipeDetailsFragmentsList.class.getSimpleName();

    public RecipeDetailsFragmentsList(FragmentToActivityListener activity){
        this.activity=activity;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.recipedetaillist,container,false);
        recipedetail=view.findViewById(R.id.recipedetails);
        adapter=new RecipeDetailListAdapter(getActivity(),this);
        adapter.setRecipe(recipe);
        LinearLayoutManager lm=new LinearLayoutManager(getActivity());
        lm.setOrientation(RecyclerView.VERTICAL);
        recipedetail.setAdapter(adapter);
        recipedetail.setLayoutManager(lm);
        Log.d(TAG,"inside oncreate view fragment"+recipe.getName());
        return view;
    }

    @Override
    public void sendData(ArrayList<Recipe> list) {
        Log.d(TAG,"inside sendData of fragment list "+list.get(0).getName());
         if(recipe==null){
             recipe=list.get(0);
         }else{
             recipe=list.get(0);
             adapter.setRecipe(recipe);
         }
    }

    @Override
    public void listOnClick(int step_position) {
        Log.d(TAG,"Inside Listonclick of RecipeDetailsFragmentList");
        activity.sendDataToActivity(step_position);
    }
}