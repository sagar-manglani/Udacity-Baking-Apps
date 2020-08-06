package com.udacity.udacitybakingapps.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.udacity.udacitybakingapps.Data.Recipe;
import com.udacity.udacitybakingapps.Interface.FragmentListOnClickListener;
import com.udacity.udacitybakingapps.Interface.FragmentToActivityListener;
import com.udacity.udacitybakingapps.Interface.PassWidgetInformation;
import com.udacity.udacitybakingapps.Interface.SendDataFromActivity;
import com.udacity.udacitybakingapps.R;
import com.udacity.udacitybakingapps.RecipeDetail;
import com.udacity.udacitybakingapps.RecyclerView.RecipeDetailListAdapter;
import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecipeDetailsFragmentsList extends Fragment implements SendDataFromActivity, FragmentListOnClickListener {

    RecyclerView recipedetail;
    Recipe recipe;
    RecipeDetailListAdapter adapter;
    FragmentToActivityListener activity;
    SharedPreferences sharedPreferences;
    int position;
    PassWidgetInformation widget_activity;
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
        sharedPreferences = getActivity().getSharedPreferences("MySharedPref", getActivity().MODE_PRIVATE);
        LinearLayoutManager lm=new LinearLayoutManager(getActivity());
        lm.setOrientation(RecyclerView.VERTICAL);
        recipedetail.setAdapter(adapter);
        recipedetail.setLayoutManager(lm);
        widget_activity=(PassWidgetInformation)getActivity();
        Log.d(TAG,"inside oncreate view fragment"+recipe.getName());
        Log.d("testing",getArguments().getString("test"));
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState!=null){
            activity = (RecipeDetail)getActivity();
        }
    }

    @Override
    public void listOnClick(int step_position) {
        Log.d(TAG,"Inside Listonclick of RecipeDetailsFragmentList "+step_position);
        activity.sendDataToActivity(step_position);
        position=step_position;
    }

    @Override
     public void onStop() {
        super.onStop();
        //Log.d("widgetupdate","RecipeDetailsFragmentList "+recipe.getName()+" "+position);
        //widget_activity.passDataForWidget(recipe,position);


    }
}
