package com.udacity.udacitybakingapps.Fragments;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.udacitybakingapps.Data.Recipe;
import com.udacity.udacitybakingapps.Interface.FragmentToActivityListener;
import com.udacity.udacitybakingapps.Interface.PassWidgetInformation;
import com.udacity.udacitybakingapps.Interface.SendDataFromActivity;
import com.udacity.udacitybakingapps.Interface.FragmentListOnClickListener;
import com.udacity.udacitybakingapps.R;
import com.udacity.udacitybakingapps.RecyclerViewDecorator.listDecorator;
import com.udacity.udacitybakingapps.RecyclerView.RecipeListAdapter;
import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecipeFragmentList extends Fragment implements SendDataFromActivity, FragmentListOnClickListener {
    RecyclerView recipeList;
    RecipeListAdapter adapter;
    ArrayList<Recipe> list;
    FragmentToActivityListener listener;
    static String TAG= RecipeFragmentList.class.getSimpleName();
    PassWidgetInformation activity;
    int recipe_position;
    private static Boolean updateWidget=false;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recipelist,container,false);
        recipeList=view.findViewById(R.id.recipe_list);
        adapter=new RecipeListAdapter(getActivity(),this);
        recipeList.setAdapter(adapter);
        activity=(PassWidgetInformation)getActivity();
        adapter.setList(list);
        listDecorator decorator = new listDecorator(getResources().getDrawable(R.drawable.listdivider),10);
        recipeList.addItemDecoration(decorator);
        listener=(FragmentToActivityListener) getActivity();
        if(getActivity().getResources().getBoolean(R.bool.isTablet)&& getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            GridLayoutManager grid=new GridLayoutManager(getActivity(),2);
            recipeList.setLayoutManager(grid);
        }else{
            LinearLayoutManager lm = new LinearLayoutManager(getContext());
            lm.setOrientation(RecyclerView.VERTICAL);
            recipeList.setLayoutManager(lm);
        }
        //Log.d(TAG,"inside onCreateView "+list.get(0).getName());
        return view;

    }

    @Override
    public void sendData(ArrayList<Recipe> list) {
        Log.d("list","list is set");
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
        recipe_position=position;
        updateWidget=true;
    }

    @Override
    public void onStop() {
        super.onStop();
        if(updateWidget) {
            Log.d("widgetupdate", "RecipeFragmentList " + list.get(recipe_position).getName() + " " + 0);
            activity.passDataForWidget(list.get(recipe_position), 0);
            updateWidget=false;
        }
    }
}
